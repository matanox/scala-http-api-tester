/*
 * Result statistics
 */

package tester

import java.io.File
import com.github.tototoshi.csv.defaultCSVFormat

object ResultStats {
  
  //private val elapsedMap = new java.util.concurrent.ConcurrentHashMap[TimedHttpResponse, Long]
  //private val results = new scala.collection.concurrent.TrieMap[Int, TimedHttpResponse]
  val results = scala.collection.mutable.ArrayBuffer.empty[TimedHttpResponse] // http://docs.scala-lang.org/overviews/collections/concrete-mutable-collection-classes.html
  
  private val resultsLockObject = new Object
 
  def deposit(response: TimedHttpResponse) = resultsLockObject.synchronized {
    results += (response) 
  }
  
  def toCSV(runName: String) {
    
    import com.github.tototoshi.csv._

    val csvFileName = s"$runName-elapsed-table-${System.currentTimeMillis / 1000}.csv"  // adding seconds from epoch, to avoid overwriting existing output file
    println(s"writing output to file://${System.getProperty("user.dir")}/$csvFileName") // prints (linux terminal) clickable link to the output file
    
    val csvFile = CSVWriter.open(new File(csvFileName), append=false)
    
    /* write csv headers row */
    csvFile writeRow List("http return code", "elapsed ms", "request description", "relative start time (ms)") 
    
    /* write csv data rows */
    results foreach { result => 
      csvFile writeRow List(result.response.code, result.elapsed, result.requestDesc, result.relativeStartTime) 
    }
  }
}