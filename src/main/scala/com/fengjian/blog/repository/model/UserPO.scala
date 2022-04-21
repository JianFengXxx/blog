package com.fengjian.blog.repository.model
import java.time.{LocalDate, LocalDateTime}

case class UserPO(id: Option[Int], username: String, password: String, nickname: String, name: String, birthday: LocalDate, gender: Int, lastLogin: LocalDateTime)


