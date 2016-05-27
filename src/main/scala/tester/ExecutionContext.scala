package tester

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

/*
 * For future use ― if we want to give higher priority to server calling v.s. reporting
 * 
 * assumes we will limit the default scala execution context through -Dscala.concurrent.context.maxThreads=n,
 * and/or assign all other `Future`s a lower priority thread pool somehow 
 */
object PriorityExecutionContext {
  private val cores = Runtime.getRuntime().availableProcessors();
  val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(cores - 3)) 
}