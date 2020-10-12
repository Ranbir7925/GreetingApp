package com.bridglabz.controller

import scala.concurrent.duration.{DurationInt, FiniteDuration}

/**
 * Object for creating artificial time delay
 */
object TimeUtils  {
  val atMostDuration: FiniteDuration = 2.seconds
  val timeoutMills: Long = 2 * 1000
}
