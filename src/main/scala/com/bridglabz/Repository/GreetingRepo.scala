package com.bridglabz.user

import java.util.UUID

import com.bridglabz.db.DbConfig.greeting
import com.bridglabz.db.Greeting
import com.bridglabz.domain.GreetingRequest
import com.bridglabz.utils.JsonUtils
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
