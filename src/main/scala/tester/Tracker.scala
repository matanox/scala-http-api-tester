/*
 * Concurrency-safe object tracking pending requests, so that the program can no when everything is done
 */

package tester

import scala.concurrent.Promise

case class Tracker(expected: Int) {
 
  val startTime = System.nanoTime()
  
  private var callsPendingReponse = new java.util.concurrent.atomic.AtomicInteger; callsPendingReponse.set(0)
  private var callsResponded = new java.util.concurrent.atomic.AtomicInteger; callsResponded.set(0)
  
  /* count an additional pending call */
  def callStarting = callsPendingReponse.incrementAndGet
  
  /* uncount one pending call */
  def requestArrived = {
    val responded = callsResponded.incrementAndGet
    if (responded == expected) {
      val endTime = System.nanoTime()
      val elapsed = (endTime - startTime) / 1000 / 1000 // elapsed time in milliseconds
      donePromise success s"elapsed time: $elapsed ms" 
    }
  }
  
  private val donePromise = Promise[String]
  
  val done = donePromise.future
  
}