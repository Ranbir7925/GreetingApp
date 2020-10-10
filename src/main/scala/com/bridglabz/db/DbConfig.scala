package com.bridglabz.db

import org.bson.codecs.configuration.CodecRegistry

object DbConfig {
  import org.bson.codecs.configuration.CodecRegistries
  import org.bson.codecs.configuration.CodecRegistries._
  import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
  import org.mongodb.scala.bson.codecs.Macros._
  import org.mongodb.scala.{MongoClient, MongoCollection}

  private val registry: CodecRegistry = CodecRegistries.fromProviders(classOf[Greeting])

  val client: MongoClient = MongoClient()
  val database = client.getDatabase("test").withCodecRegistry(fromRegistries(registry, DEFAULT_CODEC_REGISTRY))
  val greeting: MongoCollection[Greeting] = database.getCollection("greeting")
}
