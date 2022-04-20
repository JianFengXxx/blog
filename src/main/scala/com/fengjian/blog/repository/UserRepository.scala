package com.fengjian.blog.repository

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.model.UserPO

trait UserRepository {

  def getUser(id: Int): IO[Either[UserNotFoundError.type , UserPO]]

  def login(username: String, password: String): IO[Either[UserNotFoundError.type , UserPO]]

  def register(user: UserPO): IO[Either[UserHasExistError.type , Unit]]

  def updateUserInfo(userId: Int, nickname: String): IO[Either[UserNotFoundError.type , String]]

}
