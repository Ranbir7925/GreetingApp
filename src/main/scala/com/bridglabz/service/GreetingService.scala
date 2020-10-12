package com.bridglabz.service

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.bridglabz.models.{Greeting, GreetingRequest}
import com.bridglabz.Repository.GreetingRepo
import org.mongodb.scala.Completed

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class GreetingService {
  /**
   * Send data to GreetingRepo which is responsible for further processing
   * @return
   */
  def saveGreetingData: GreetingRequest => Completed = (greetingRequest: GreetingRequest) => {
    Await.result(GreetingRepo.insertData(greetingRequest),Duration.Inf)
  }

  /**
   * Get all data from GreetingRepo
   * @return A stream which will be processed further
   */
  def findAll: Source[Greeting, NotUsed] = {
    Source.fromFuture(GreetingRepo.findAll())
      .mapConcat {
        identity
      }
  }
}