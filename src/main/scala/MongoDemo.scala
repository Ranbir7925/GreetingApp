
import org.mongodb.scala._
object MongoDemo extends App {
  val uri: String = "mongodb://127.0.0.1:27017/?gssapiServiceName=mongodb"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("demo")
  val Collection = db.getCollection("new")
  val document = Collection.find()
  //  println(document.collect().toString)
  Collection.find().collect().subscribe((results: Seq[Document]) => println(s"Found: #${results.size}"))
  println("ds")
  //  client.listDatabaseNames().foreach(println(_))
  //  val document = Collection.find()
  //  document.foreach(println(_))
  //  println(db.)
}
