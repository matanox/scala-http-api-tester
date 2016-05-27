# Create repeatable concurrency tests for your HTTP API server
Runs a plain Scala specified scenario of http api calls, recording their result and latency to the console and a CSV file.
Note that it can (currently) only go as fast as it is able to generate requests, not infinitely fast.

Not yet entirely made a generic library, package `samples` shows intended usage.

## Prerequisites

Need to have sbt, the scala build tool, installed

# Usage
In sbt, (or activator) type `run-main` then tab, to select between different scenarios to run.

It is currently necessary (but highly recommended!) to use Oracle Java VisualVM to watch _load on the play server_ while this application is being run.

To limit the amount of processors available to the play server being tested, or prevent the server and this test app from competing over the same processor time, on linux, use the `taskset` shell command:

E.g. start play with `taskset -c 0-3 sbt run` to simulate a server with 4 processors. You can then run this app with `taskset -c 4-7` to reduce cyclic performance effects between the play server and this application in case they are both running on the same server. This example assumed you have 8 cores..

https://github.com/matanster/scala-http-api-tester/issues/1 is currently blocking when the pace of simulated requests is high (up to 100 requests per second should be fine, but 400 is already a problem).
