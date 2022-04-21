package com.fengjian.blog.mock.normal

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.UserRepository
import com.fengjian.blog.repository.model.{UserPO}
import com.fengjian.blog.router.model.user.{QuestionDTO, RetrievePasswordDTO}

import java.time.{LocalDate, LocalDateTime}

class MockUserRepositoryNormal extends UserRepository{

  override def getUser(id: Int): IO[Either[UserNotFoundError.type, UserPO]] = {
    val user = Right(UserPO(Option(1), "test", "test","test", "test", LocalDate.now(), 1, LocalDateTime.now()))
    IO.pure(user)
  }

  override def login(username: String, password: String): IO[Either[UserNotFoundError.type, UserPO]] = {
    val login = Right(UserPO(Option(1), "test", "test", "test", "test", LocalDate.now(), 1, LocalDateTime.now()))
    IO.pure(login)
  }

  override def register(user: UserPO): IO[Either[UserHasExistError.type, Unit]] = {
    IO.pure(Right((): Unit))
  }

  override def updateUserInfo(userId: Int, nickname: String): IO[Either[UserNotFoundError.type, String]] = {
    IO.pure(Right("xxx"))
  }

  override def getUserInfo(username: String): IO[Either[UserNotFoundError.type, UserPO]] = ???

  override def getUserQuestions(userId: Int): IO[List[QuestionDTO]] = ???

  override def refreshLastLogin(userId: Int): IO[Int] = IO(1)
}
