package com.bridglabz.models

/**
 * Class accepting Sting Value parameters
 * @param name -> In String Format
 * @param message-> In String Format
 */
case class GreetingRequest(name: String, message: String) {
  require(!name.isEmpty, "Name Can't be Empty")
  require(!message.isEmpty, "Message Can't be Empty")
}
