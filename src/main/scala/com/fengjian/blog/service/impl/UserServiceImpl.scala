package com.fengjian.blog.service.impl

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.UserRepository
import com.fengjian.blog.repository.model.{UserPO}
import com.fengjian.blog.router.model.user.{QuestionDTO, RetrievePasswordDTO, UserCreateDTO, UserLoginDTO, UserUpdateDTO}
import com.fengjian.blog.service.UserService
import com.fengjian.blog.service.model.UserInfoResponse


class UserServiceImpl(repository: UserRepository) extends UserService {

  override def updateUserInfo(userUpdateDTO: UserUpdateDTO): IO[Either[UserNotFoundError.type, String]] = {
    repository.updateUserInfo(userUpdateDTO.id, userUpdateDTO.nickname)
  }

  override def register(userCreateInfo: UserCreateDTO): IO[Either[UserHasExistError.type, Unit]] = {
    val userPO: UserPO = userCreateInfo.convert2PO()
    repository.register(userPO)
  }

  override def getUser(id: Int): IO[Either[UserNotFoundError.type, UserInfoResponse]] = {
    for {
      userPO <- repository.getUser(id)
      userInfoDTO <- userPO match {
        case Right(user) => IO(Right(UserInfoResponse(user.id.get, user.username, user.nickname, user.name, user.birthday, user.gender, user.lastLogin)))
        case Left(ex) => IO(Left(ex))
      }
    } yield userInfoDTO
  }

  override def login(userLogin: UserLoginDTO): IO[Either[UserNotFoundError.type, UserInfoResponse]] = {
    for {
      userPO <- repository.login(userLogin.username, userLogin.password)
      userInfoDTO <- userPO match {
        case Right(user) =>
          for {
            _ <- repository.refreshLastLogin(user.id.get)
            userInfo = Right(UserInfoResponse(user.id.get, user.username, user.nickname, user.name, user.birthday, user.gender, user.lastLogin))
          } yield userInfo
        case Left(ex) => IO(Left(ex))
      }
    } yield userInfoDTO
  }

  override def retrievePassword(retrievePasswordDTO: RetrievePasswordDTO): IO[Either[UserNotFoundError.type, String]] = {
    for {
      userInfo <- repository.getUserInfo(retrievePasswordDTO.username)
      result <- userInfo match {
        case Right(user) => checkQuestions(user, retrievePasswordDTO.questions)
        case Left(_) => IO(Left(UserNotFoundError))
      }
    } yield result
  }

  private def checkQuestions(userInfo: UserPO, questions: List[QuestionDTO]): IO[Either[UserNotFoundError.type, String]] = {
    for {
      questionsFromDB <- repository.getUserQuestions(userInfo.id.get)
      password = if (questions == questionsFromDB) Right(userInfo.password) else Left(UserNotFoundError)
    } yield password
  }

}
