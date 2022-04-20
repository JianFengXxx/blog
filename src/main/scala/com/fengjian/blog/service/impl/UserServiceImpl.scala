package com.fengjian.blog.service.impl

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.UserRepository
import com.fengjian.blog.repository.model.{UserBasicInfoPO, UserPO}
import com.fengjian.blog.router.model.user.{RetrievePasswordDTO, QuestionDTO, UserCreateDTO, UserLoginDTO, UserUpdateDTO}
import com.fengjian.blog.service.UserService

class UserServiceImpl(repository: UserRepository) extends UserService {

  override def updateUserInfo(userUpdateDTO: UserUpdateDTO): IO[Either[UserNotFoundError.type, String]] = {
    repository.updateUserInfo(userUpdateDTO.id, userUpdateDTO.nickname)
  }

  override def register(userCreateInfo: UserCreateDTO): IO[Either[UserHasExistError.type, Unit]] = {
    val registerPo: UserPO = userCreateInfo.convert2PO()
    repository.register(registerPo)
  }

  override def getUser(id: Int): IO[Either[UserNotFoundError.type, UserPO]] = {
    repository.getUser(id)
  }

  override def login(userLogin: UserLoginDTO): IO[Either[UserNotFoundError.type, UserPO]] = {
    repository.login(userLogin.username, userLogin.password)
  }

  override def retrievePassword(retrievePasswordDTO: RetrievePasswordDTO): IO[Either[UserNotFoundError.type, String]] = {
    for {
      userBasicInfo <- repository.checkUserInfo(retrievePasswordDTO)
      result <- userBasicInfo match {
        case Right(userBasicPO) =>
          for {
            checkResult <- checkQuestions(userBasicPO.id, retrievePasswordDTO.questions)
            password = if (checkResult) {
              Right(userBasicPO.password)
            } else {
              Left(UserNotFoundError)
            }
          } yield password
        case Left(_) => IO(Left(UserNotFoundError))
      }
    } yield result
  }

  private def checkQuestions(userId: Int, questions: List[QuestionDTO]): IO[Boolean] = {
    for {
      questionsFromDB <- repository.getUserQuestions(userId)
      password = if (questions == questionsFromDB)  true else false
    } yield password
  }

}
