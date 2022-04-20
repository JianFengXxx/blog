package com.fengjian.blog.mock.normal

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.UserRepository
import com.fengjian.blog.repository.model.{UserBasicInfoPO, UserPO}
import com.fengjian.blog.router.model.user.{RetrievePasswordDTO, QuestionDTO}

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

  override def checkUserInfo(forgotPasswordDTO: RetrievePasswordDTO): IO[Either[UserNotFoundError.type, UserBasicInfoPO]] = ???

  override def getUserQuestions(userId: Int): IO[List[QuestionDTO]] = ???
}
