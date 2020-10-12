package com.bridglabz.Repository

import java.util.UUID

import com.bridglabz.db.DbConfig.greeting
import com.bridglabz.models.{Greeting, GreetingRequest}
import com.bridglabz.controller.JsonUtils
import org.mongodb.scala.Completed

import scala.concurrent.Future

object GreetingRepo extends JsonUtils {
  var greetingDoc = greeting

  /**
   * For insertion of data in real database
   * @param user -> Object of GreetingRequest class
   * @return Future Completed result
   */
  def insertData(user: GreetingRequest): Future[Completed] = {
    val myUser = Greeting(UUID.randomUUID.toString, user.name, user.message)
    greetingDoc.insertOne(myUser).toFuture()
  }

  /**
   * For Getting all data from real database
   * @return Seq of Greeting data
   */
  def findAll(): Future[Seq[Greeting]] = {
    greetingDoc.find().toFuture()
  }
}
