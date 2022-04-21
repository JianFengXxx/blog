package com.fengjian.blog.repository

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.model.{QuestionPO, UserPO}
import com.fengjian.blog.router.model.request.user.QuestionDTO

trait UserRepository {

  def getUser(id: Int): IO[Either[UserNotFoundError.type , UserPO]]

  def login(username: String, password: String): IO[Either[UserNotFoundError.type , UserPO]]

  def register(userPO: UserPO): IO[Either[UserHasExistError.type , Unit]]

  def updateUserInfo(userId: Int, nickname: String): IO[Either[UserNotFoundError.type , String]]

  def getUserInfo(username: String): IO[Either[UserNotFoundError.type, UserPO]]

  def getUserQuestions(userId: Int): IO[List[QuestionDTO]]

  def refreshLastLogin(userId: Int): IO[Int]

}
