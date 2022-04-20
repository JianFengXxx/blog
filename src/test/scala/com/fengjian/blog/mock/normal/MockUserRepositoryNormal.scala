package com.fengjian.blog.mock.normal

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.UserRepository
import com.fengjian.blog.repository.model.UserPO

class MockUserRepositoryNormal extends UserRepository{

  override def getUser(id: Int): IO[Either[UserNotFoundError.type, UserPO]] = {
    val user = Right(new UserPO(Option(1), "test", "test","test"))
    IO.pure(user)
  }

  override def login(username: String, password: String): IO[Either[UserNotFoundError.type, UserPO]] = {
    val login = Right(new UserPO(Option(1), "test", "test", "test"))
    IO.pure(login)
  }

  override def register(user: UserPO): IO[Either[UserHasExistError.type, Unit]] = {
    IO.pure(Right((): Unit))
  }

  override def updateUserInfo(userId: Int, nickname: String): IO[Either[UserNotFoundError.type, String]] = {
    IO.pure(Right("xxx"))
  }

}
