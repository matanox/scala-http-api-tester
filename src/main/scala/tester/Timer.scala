package tester

import scala.concurrent.{Future, Promise}
import java.util.{Timer => JavaTimer}
import java.util.TimerTask
import scala.util.Try
import java.util.{Timer => JavaTimer}

/*
 * TODO: can execute a block argument in a JavaTimer.schedule instead.... for having one thread less...
 * 
 * A timer provided as an empty future that completes after a given time. 
 * The gory details: uses a java timer to complete a future returned by itself
 * code credit: http://stackoverflow.com/a/29691518/1509695 
 */
object Timer {
  def completeAfter(duration: Long): Future[Unit] = {
    val promise = Promise[Unit]
    
    val javaTimer = new JavaTimer
    javaTimer.schedule(
      new TimerTask {
        override def run() = { promise.complete(Try(Unit)) }
      }, duration)
    
    promise.future
  }
}