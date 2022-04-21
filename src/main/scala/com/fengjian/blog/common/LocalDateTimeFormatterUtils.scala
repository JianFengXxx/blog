package com.fengjian.blog.common

import io.circe.Encoder

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeFormatterUtils {

  private val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  implicit val dateEncoder: Encoder[LocalDateTime] = Encoder.encodeString.contramap[LocalDateTime](_.format(fmt))

}
