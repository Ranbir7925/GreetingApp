
import GreetingRepository.{Greeting, GreetingDB, UserNotFoundException}
import RepositoryContext.scheduler
import akka.actor.TypedActor.dispatcher

import scala.concurrent.Future

object GreetingRepository {

  case class Greeting(id: String,
                      firstName: String,
                      lastName: String,
                      username: String,
                      message: String)

  val GreetingDB = Seq(
    Greeting("100", "Lala", "Lee", "lallee", "hey my first message"),
    Greeting("101", "Lala", "Lee", "lallee", "hey my first message"),
    Greeting("102", "Lala", "Lee", "lallee", "hey my first message"),

  )

  class UserNotFoundException extends Throwable("No user found in the database.")

}

trait GreetingRepository {


  import akka.pattern.after
  import scala.concurrent.duration._
  def fetchDBWithDelay(): Future[Seq[Greeting]] = {
    val randomDuration = (Math.random() * 5 + 3).toInt.seconds
    after(randomDuration, scheduler) {
      Future {
        GreetingDB
      }
    }
  }

  def getUserById(id: String) = fetchDBWithDelay().map { db =>
    val data = db.filter(_.id == id)
    if (data.isEmpty)
      throw new UserNotFoundException
    else
      data(0)
  }

  def getAllUser() = fetchDBWithDelay().map { db =>
    val data = db
    if (data.isEmpty)
      throw new UserNotFoundException
    else
      data(0)
  }

  def queryUser(id: String, firstName: String, lastName: String, message: String): Future[Seq[Greeting]] = {
    fetchDBWithDelay().map { db =>

      val data = db.filter { elem =>
        elem.id == id && elem.firstName == firstName && elem.lastName == lastName && elem.message == message
      }
      data
    }
  }
}