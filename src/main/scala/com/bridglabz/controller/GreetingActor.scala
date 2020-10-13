package com.bridglabz.controller

import akka.actor.{Actor, ActorLogging}
import com.bridglabz.models.GreetingRequest
import com.bridglabz.service.GreetingService

/**
 * Class in which actors are used for communication
 */
class GreetingActor extends Actor with ActorLogging {
  private val greetingService: GreetingService = new GreetingService()

  /**
   * For creating actors we have to extend actor trait and implement receive
   *
   * @return This function returns a result to Ask()
   */
  override def receive: Receive = {

    case SAVE(greeting: GreetingRequest) =>
      log.info(s"received message Save $greeting")
      sender ! greetingService.saveGreetingData(greeting)
    case SEARCH_ALL =>
      log.info(s"received message find all")
      sender ! greetingService.findAll
    case SEARCH_Id(id) =>
      log.info(s"received message received for the id: $id")
      sender ! greetingService.findId(id)
    case _ =>
      log.debug("Unhandled message!")
  }
}


sealed trait GreetingActorMessage

case class SAVE(greeting: GreetingRequest) extends GreetingActorMessage

case object SEARCH_ALL extends GreetingActorMessage

case class SEARCH_Id(id: String) extends GreetingActorMessage