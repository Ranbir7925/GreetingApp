package com.bridglabz.models

case class GreetingRequest(name: String, message: String) {
  require(!name.isEmpty, "Name Can't be Empty")
  require(!message.isEmpty, "Message Can't be Empty")
}
