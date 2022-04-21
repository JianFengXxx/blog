package com.fengjian.blog.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeFormatterUtils {

  private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def dateTimeFormatter(localDateTime: LocalDateTime): String = localDateTime.format(DATE_TIME_FORMATTER)

}
