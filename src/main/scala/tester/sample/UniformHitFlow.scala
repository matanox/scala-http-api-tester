package tester.sample;

import tester._

import scala.concurrent.Future
import scalaj.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.annotation.tailrec
import scala.concurrent.Await
import scala.util.{Try, Success, Failure}
import scala.concurrent.duration._
import ApiRequests._
import org.apache.commons.math3.distribution.PoissonDistribution
import org.apache.commons.math3.distribution.UniformRealDistribution
import scala.concurrent.ExecutionContext.Implicits.global
import sample.ApiRequests._


//val dist = new UniformRealDistribution
//val requestTimes = Seq.fill(requestNum)(dist.sample) // Stream.continually(dist.sample).take(20)
//val Ids = Seq.fill(bundleRepeat)(random.nextInt).toIterator

object UniformHitFlow extends App with AsyncRequest {  

  val duration = 60 * 30 // video duration in seconds

  def requestBundle(id: Int) =
    Seq(
      /* enter calls from ApiRequests here */
    )
  
  
  val random = new scala.util.Random(100) // arbitrary random seed for consistency across runs
  
  val bundleRepeat = 1000 // how many times to repeat the request bundle
  
  val hitsPerSecond = 100
  
  val requestsNum = bundleRepeat * requestBundle(0).size
  
  implicit val flowTracker = Tracker(requestsNum)
  
  def specificRequestBundle = requestBundle(random.nextInt)  
   
  val requestStream: Stream[RequestWithDescription] = Stream.continually(specificRequestBundle).flatten.take(requestsNum) 
  
  println("test starting") 
  
  val results = requestStream.map { request =>
    Thread.sleep(1000 / hitsPerSecond)
    asyncHttp(request) 
  } // TODO: // this can go to a high priority execution context, e.g. via `(PriorityExecutionContext.ec)`
  
  results.toList map { AsyncResponseReporter(_) } // TODO: this can go to a low priority execution context
   
  /*
  val calls = Seq(
    GetTimesRequest(id, bta),
    AdSeenRequest(id, 30),
    AdClickedRequest(id, 40)
  )

  //implicit val ec = PriorityExecutionContext.ec
  AsyncResponseReporter(asyncHttp(RegisterRequest(id, playerType, duration, bta))).onComplete { case _ =>
    Timer.completeAfter(ServerConcurrencyGlitchWorkarounds.registerCompletionGrace).onComplete { case _ => 
      val results = calls map { request => asyncHttp(request) }
      results map { AsyncResponseReporter(_) }
    }   
  } 

  */
  Await.result(flowTracker.done, Duration(1, MINUTES))
  
  flowTracker.done.onComplete {
    case Failure(f) => 
      println("something unexpected happened with this load simulator")
    case Success(s) =>
      println(s)
      
      val runName = args.isEmpty match {
        case true  => 
          new java.util.Date toString
        case false =>
          if (args.length>1) println("warning: ignoring superfluous run arguments, only a single argument is allowed")
          args.head
      }
      
      ResultStats.toCSV(runName)     
  }
 
}