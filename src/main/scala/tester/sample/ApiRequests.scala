package tester.sample

import tester._
import scalaj.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/*
 * The requests making up the API we are driving in this test project ―
 * this should mirror the routes file of the application being tested, if testing a Play application.
 */

// TODO: extract to configuration
class ApiRequest( // class providing default server connection settings
  val protocol: String = "http",
  val host: String = "localhost",
  val port: String = "9000"
)
    
/* class for attaching a description to a request */
case class RequestWithDescription(req: HttpRequest, desc: String)

/*
 * Sample object containing http request objects ― that would be used in the scenario files.
 * This is where you define the requests your scenarios will be making
 */
object ApiRequests extends ApiRequest {  
  
  // add real http request content here inside
  object EmptyRequest1 {
    def apply = 
      RequestWithDescription(
        HttpClient(s"$protocol://$host:$port/")
          .put("")
          .header("Content-Type", "application/json"),
        s"some request description"
      )
  }
  
  // add real http request content here inside
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