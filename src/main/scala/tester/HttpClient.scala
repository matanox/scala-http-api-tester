package tester

import scalaj.http._

/* Just set up our http client object with a minor override */
object HttpClient extends BaseHttp (userAgent = "test")