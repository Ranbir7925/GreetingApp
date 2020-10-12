package com.bridglabz.service

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.bridglabz.models.{Greeting, GreetingRequest}
import com.bridglabz.Repository.GreetingRepo
import org.mongodb.scala.Completed

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class GreetingService {

  def saveGreetingData: GreetingRequest => Completed = (greetingRequest: GreetingRequest) => {
    Await.result(GreetingRepo.insertData(greetingRequest),Duration.Inf)
  }

  def findAll: Source[Greeting, NotUsed] = {
    Source.fromFuture(GreetingRepo.findAll())
      .mapConcat {
        identity
      }
  }
//
//  def findByID:Source[Greeting,NotUsed]={
//    Source.fromFuture(GreetingRepo.findById(id))
//      .mapConcat{
//        identity
//      }
//  }

}