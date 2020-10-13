name := "RestAPI"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.26",
  "com.typesafe.akka" %% "akka-http" % "10.1.11",
  "com.typesafe.akka" %% "akka-stream" % "2.5.26",
  "ch.rasc" % "bsoncodec" % "1.0.1",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.7.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.26",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.5.26" % Test,
  "org.clapper" %% "grizzled-slf4j" % "1.3.4",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

