package com.bridglabz.Repository

import java.util.UUID

import com.bridglabz.db.DbConfig.greeting
import com.bridglabz.models.{Greeting, GreetingRequest}
import org.mongodb.scala.Completed
import org.mongodb.scala.model.Filters.equal

import scala.concurrent.Future

object GreetingRepo {
  var greetingDoc = greeting

  /**
   * For insertion of data in real database
   *
   * @param user -> Object of GreetingRequest class
   * @return Future Completed result
   */
  def insertData(user: GreetingRequest): Future[Completed] = {
    val myUser = Greeting(UUID.randomUUID.toString, user.name, user.message)
    greetingDoc.insertOne(myUser).toFuture()
  }

  /**
   * For Getting all data from real database
   *
   * @return Seq of Greeting data
   */
  def findAll(): Future[Seq[Greeting]] = {
    greetingDoc.find().toFuture()
  }

  /**
   * For Getting all data for provided id from real database
   *
   * @return Data by id provided
   */
  def findID(id: String): Future[Greeting] = {
    greetingDoc.find(equal("_id", id)).head()
  }
}
