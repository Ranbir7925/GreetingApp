package com.bridglabz.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.bridglabz.models.{Greeting, GreetingRequest}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonUtils extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val greetingJsonFormatter: RootJsonFormat[Greeting] = DefaultJsonProtocol.jsonFormat3(Greeting)
  implicit val greetingRequestFormat: RootJsonFormat[GreetingRequest] = jsonFormat2(GreetingRequest)
}