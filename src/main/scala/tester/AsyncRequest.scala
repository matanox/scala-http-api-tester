package tester

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scalaj.http._
import sample.RequestWithDescription

trait AsyncRequest {
  
  /*
   * Makes an async request while (approximately) measuring it latency
   */
  def asyncHttp(r: RequestWithDescription)(implicit flowTracker:Tracker): Future[TimedHttpResponse] = {
       
    val response = Future {
      
      flowTracker.callStarting
      
      val startTime = System.nanoTime() // as is, it is only a lower bound for the real time that the http request goes out on the network
      
      println(s"${Console.MAGENTA}${startTime / 1000 / 1000} - making api call ${Console.MAGENTA}${r.desc} → ${r.req.url}${Console.RESET}") // trace the request being made.
      
      // send the request
      val response = r.req.asString
      
      // compute elapsed time etc.
      val endTime = System.nanoTime()
      val elapsed = (endTime - startTime) / 1000 / 1000 // elapsed time in milliseconds
      
      flowTracker.requestArrived
      
      TimedHttpResponse(r.desc, startTime / 1000, endTime / 1000, elapsed, response)
      
    } // (PriorityExecutionContext.ec) // use priority execution context
         
    response
  }
}