/* a test application for the application */
lazy val appTest = (project in file("."))
  .settings(
    name := "http-api-test",
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      /* json */
      "com.typesafe.play" %% "play-json" % "2.4.6",
      /* http client */
      "org.scalaj" %% "scalaj-http" % "2.3.0",
      /* nicer scala files IO */
      "com.github.pathikrit" %% "better-files" % "2.16.0",
      /* statistics functions */
      "org.apache.commons" % "commons-math3" % "3.5",
      /* safely escaped csv writing */
      "com.github.tototoshi" %% "scala-csv" % "1.3.0"
    ),
    fork in run := false,
    publishArtifact := false)
