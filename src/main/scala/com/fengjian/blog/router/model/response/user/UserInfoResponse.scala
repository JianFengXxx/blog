package com.fengjian.blog.router.model.response.user

import com.fengjian.blog.common.LocalDateTimeFormatterUtils
import io.circe.syntax._
import io.circe.{Encoder, Json}

import java.time.{LocalDate, LocalDateTime}

case class UserInfoResponse(id: Int, username: String, nickname: String, name: String, birthday: LocalDate, gender: Int, lastLogin: LocalDateTime)

object UserInfoResponse {

  implicit val encodeFoo: Encoder[UserInfoResponse] = new Encoder[UserInfoResponse] {
    final def apply(userInfo: UserInfoResponse): Json = {
      Json.obj(
        "id" -> userInfo.id.asJson,
        "username" -> userInfo.username.asJson,
        "nickname" -> userInfo.nickname.asJson,
        "name" -> userInfo.name.asJson,
        "birthday" -> userInfo.birthday.asJson,
        "gender" -> userInfo.gender.asJson,
        "lastLogin" -> LocalDateTimeFormatterUtils.dateTimeFormatter(userInfo.lastLogin).asJson
      )
    }
  }

}
