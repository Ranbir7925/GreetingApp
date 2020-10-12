package com.bridglabz.Repository

import java.util.UUID

import com.bridglabz.db.DbConfig.greeting
import com.bridglabz.models.{Greeting, GreetingRequest}
import com.bridglabz.controller.JsonUtils
import org.mongodb.scala.Completed

import scala.concurrent.Future

object GreetingRepo extends JsonUtils {
  var greetingDoc = greeting

  def insertData(user: GreetingRequest): Future[Completed] = {
    val myUser = Greeting(UUID.randomUUID.toString, user.name, user.message)
    greetingDoc.insertOne(myUser).toFuture()
  }

  def findAll(): Future[Seq[Greeting]] = {
    greetingDoc.find().toFuture()
  }
//  def findById(id:String):Future[Seq[Greeting]] = {
//    greetingDoc.find(_id == id).toFuture()
//  }
}
