package com.bridglabz.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.bridglabz.GreetingRouteConfig

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

/**
 * Object for creating Server using system,dispatcher,materialize
 */
object WebServer extends App {
  implicit val system: ActorSystem = ActorSystem("web-app")
  private implicit val dispatcher: ExecutionContextExecutor = system.dispatcher
  private implicit val materialize: ActorMaterializer = ActorMaterializer()

  private val routeConfig = new GreetingRouteConfig()

  val serverFuture = Http().bindAndHandle(routeConfig.getRoute, "localhost", 8080)

  println("Server started ...")
  StdIn.readLine()
  serverFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
