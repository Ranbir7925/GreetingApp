package com.bridglabz.models

/**
 * Simple case class which accept data in string
 * Documents in database will be created respective to parameters of this class
 * @param _id -> In String Format
 * @param name -> In String Format
 * @param message -> In String Format
 */
case class Greeting(_id: String, name: String, message: String)
