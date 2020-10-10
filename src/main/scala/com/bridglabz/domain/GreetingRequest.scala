package com.bridglabz.domain

case class GreetingRequest(name: String, message: String){
  require(!name.isEmpty,"Name Can't be Empty")
  require(!message.isEmpty,"Message Can't be Empty")
}
