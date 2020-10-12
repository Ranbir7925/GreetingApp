package com.bridglabz.db

import com.bridglabz.models.Greeting
import org.bson.codecs.configuration.CodecRegistries._
import org.bson.codecs.configuration.{CodecRegistries, CodecRegistry}
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.{MongoClient, MongoCollection}

object DbConfig {

  private val registry: CodecRegistry = CodecRegistries.fromProviders(classOf[Greeting])
  val client: MongoClient = MongoClient()
  val database = client.getDatabase("test").withCodecRegistry(fromRegistries(registry, DEFAULT_CODEC_REGISTRY))
  val greeting: MongoCollection[Greeting] = database.getCollection("greeting")
}
