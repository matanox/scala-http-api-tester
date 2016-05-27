package tester.sample

import tester._
import sample.ApiRequests._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scalaj.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.annotation.tailrec
import scala.concurrent.Await
import scala.util.{Try, Success, Failure}
import scala.concurrent.duration._

object NormalFlowTest extends App with AsyncRequest {  

  implicit val flowTracker = Tracker(4) 
  
  println("test starting") 
  
  val random = new scala.util.Random(100) // arbitrary random seed for consistency across runs
  
  val id = random.nextInt
  val duration = 1000
  
  val calls = Seq(
    /* enter calls from ApiRequests here */
  )

  AsyncResponseReporter(asyncHttp(EmptyRequest1.apply)).onComplete { case _ =>
    Timer.completeAfter(ServerConcurrencyGlitchWorkarounds.registerCompletionGrace).onComplete { case _ => 
      val results = calls map { request => asyncHttp(request) }
      results map { AsyncResponseReporter(_) }
    }  
  }

  Await.result(flowTracker.done, Duration(1, MINUTES))
  
  flowTracker.done.onComplete {
    case Success(s) => println(s)
    case Failure(f) => println("something unexpected happened with this load simulator")
  }
 
}