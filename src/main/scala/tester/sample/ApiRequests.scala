package tester.sample

import tester._
import scalaj.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/*
 * 
 * The requests making up the API we are driving in this test project ―
 * this should mirror the routes file of the application being tested.
 * 
 */

class ApiRequest( // class providing default server connection settings
  val protocol: String = "http",
  val host: String = "localhost",
  val port: String = "9000"
)
    
case class RequestWithDescription(req: HttpRequest, desc: String) // class for attaching a description to a request

/*
 * factories per request type 
 */
object ApiRequests extends ApiRequest {  
  
  object EmptyRequest1 {
    def apply = 
      RequestWithDescription(
        HttpClient(s"$protocol://$host:$port/")
          .put("")
          .header("Content-Type", "application/json"),
        s"some request description"
      )
  }
  
  object EmptyRequest2 {
    def apply =
      RequestWithDescription(
        HttpClient(s"$protocol://$host:$port/")
          .param("sec", "")
          .method("POST"),
        s"some request description"
      )
  }
} 