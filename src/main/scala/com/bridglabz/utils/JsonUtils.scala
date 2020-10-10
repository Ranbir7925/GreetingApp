package com.bridglabz.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.bridglabz.db.Greeting
import com.bridglabz.domain.GreetingRequest
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
trait JsonUtils extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val greetingJsonFormatter: RootJsonFormat[Greeting] = DefaultJsonProtocol.jsonFormat3(Greeting)
  implicit val greetingRequestFormat: RootJsonFormat[GreetingRequest] = jsonFormat2(GreetingRequest)
}