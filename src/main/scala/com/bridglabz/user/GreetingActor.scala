package com.bridglabz.user

import akka.actor.{Actor, ActorLogging}
import com.bridglabz.domain.GreetingRequest
import com.bridglabz.service.GreetingService


class GreetingActor extends Actor with ActorLogging {
  private val greetingService: GreetingService = new GreetingService()

  override def receive: Receive = {

    case SAVE(greeting: GreetingRequest) =>
      log.info(s"received message Save $greeting")
      sender ! greetingService.saveGreetingData(greeting)
    case SEARCH_ALL =>
      log.info(s"received message find all")
      sender ! greetingService.findAll
//    case SEARCH_BY_ID(_id) =>
//      log.info(s"received message find all $_id")
//      sender ! greetingService.findByID(_id)
    case _ =>
      log.debug("Unhandled message!")
  }
}

sealed trait GreetingActorMessage

case class SAVE(greeting: GreetingRequest) extends GreetingActorMessage

case object SEARCH_ALL extends GreetingActorMessage

//case class SEARCH_BY_ID(_id:String) extends GreetingActorMessage