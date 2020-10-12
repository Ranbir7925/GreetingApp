package com.bridglabz.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.concat
import akka.http.scaladsl.server.directives.PathDirectives.pathPrefix
import akka.stream.ActorMaterializer
import com.bridglabz.GreetingRouteConfig

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object WebServer extends App {
  implicit val system: ActorSystem = ActorSystem("web-app")
  private implicit val dispatcher: ExecutionContextExecutor = system.dispatcher
  private implicit val materialize: ActorMaterializer = ActorMaterializer()


  private val routeConfig = new GreetingRouteConfig()

  lazy val routes = {
    pathPrefix("api") {
      concat(
        routeConfig.getRoute
      )
    }
  }

  val serverFuture = Http().bindAndHandle(routes, "localhost", 8080)

  println("Server started ...")
  StdIn.readLine()
  serverFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
