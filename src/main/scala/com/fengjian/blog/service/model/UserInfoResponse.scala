package com.fengjian.blog.service.model

import java.time.{LocalDate, LocalDateTime}
import io.circe.{Encoder, Json}
import io.circe.syntax._
import com.fengjian.blog.common.LocalDateTimeFormatterUtils._

case class UserInfoResponse(id: Int, username: String, nickname: String, name: String, birthday: LocalDate, gender: Int, lastLogin: LocalDateTime)

object UserInfoResponse {

  implicit val encodeFoo: Encoder[UserInfoResponse] = new Encoder[UserInfoResponse] {
    final def apply(userInfo: UserInfoResponse): Json = {
      Json.obj(
        "id" -> Json.fromInt(userInfo.id),
        "username" -> Json.fromString(userInfo.username),
        "nickName" -> Json.fromString(userInfo.nickname),
        "name" -> Json.fromString(userInfo.name),
        "birthday" -> userInfo.birthday.asJson,
        "gender" -> Json.fromInt(userInfo.gender),
        "lastLogin" -> userInfo.lastLogin.asJson
      )
    }
  }

}