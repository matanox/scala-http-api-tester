package tester

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Success, Failure}

object AsyncResponseReporter {
  def apply(future: Future[TimedHttpResponse]) = {
    future.onComplete { 
            
      case Success(timedHttpResponse) =>
        
        ResultStats.deposit(timedHttpResponse)
        
        timedHttpResponse.response.isSuccess match {
          
          case true  =>
            // this is when a regular response was received from the server 
            println(s"${Console.GREEN}${Console.BOLD}Okay (${timedHttpResponse.response.code}) | ${timedHttpResponse.elapsed} ms${Console.RESET} ${timedHttpResponse.requestDesc}")
            //val bodyAsJson: JsValue = Json.parse(response.body)
            
          case false => 
            // this is when the response from the server indicates a failure, assuming server is correctly using http codes
            println(s"${Console.RED}${Console.BOLD}Fail (${timedHttpResponse.response.code}) | ${timedHttpResponse.elapsed} ms${Console.RESET} ${timedHttpResponse.requestDesc}") 
        }
      
      case Failure(f) => 
        // this is when we could not make the request (server is down, no connectivity, bad address..) 
        println(s"${Console.RED}${Console.BOLD}$f${Console.RESET}")
        
    } 
    Future.successful(Unit) 
  }
}
