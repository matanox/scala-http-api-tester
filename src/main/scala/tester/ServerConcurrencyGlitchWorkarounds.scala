/*
 * Some server behaviors might not be significant yet strictly speaking are a concurrency glitch.
 * For example in the timing engine, after the server acknowledges registering a video, a subsequent call
 * to its REST endpoints for the same video id will fail if made quickly enough thereafter.
 *
 * We use this object to denote all methods and values which are meant to mitigate such concurrency glitches,
 * as long as we do not fix those glitches.  
 */

package tester

object ServerConcurrencyGlitchWorkarounds {
  var registerCompletionGrace = 100 // ms
}