package com.fengjian.blog.mock.abnormal

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.UserRepository
import com.fengjian.blog.repository.model.{UserPO}
import com.fengjian.blog.router.model.user.{RetrievePasswordDTO, QuestionDTO}

class MockUserRepositoryAbnormal extends UserRepository{

  override def getUser(id: Int): IO[Either[UserNotFoundError.type, UserPO]] = {
    IO.pure(Left(UserNotFoundError))
  }

  override def login(username: String, password: String): IO[Either[UserNotFoundError.type, UserPO]] = {
    IO.pure(Left(UserNotFoundError))
  }

  override def register(user: UserPO): IO[Either[UserHasExistError.type, Unit]] = {
    IO.pure(Left(UserHasExistError))
  }

  override def updateUserInfo(userId: Int, nickname: String): IO[Either[UserNotFoundError.type, String]] = {
    IO.pure(Left(UserNotFoundError))
  }

  override def getUserInfo(username: String): IO[Either[UserNotFoundError.type, UserPO]] = ???

  override def getUserQuestions(userId: Int): IO[List[QuestionDTO]] = ???

  override def refreshLastLogin(userId: Int): IO[Int] = ???
}
