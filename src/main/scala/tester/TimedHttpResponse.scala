package tester

import scalaj.http._

case class TimedHttpResponse(
  requestDesc: String, 
  relativeStartTime: Long, // relative time as explained in java.lang.System
  relativeEndTime: Long,   // relative time as explained in java.lang.System
  elapsed: Long, 
  response: HttpResponse[String]
)