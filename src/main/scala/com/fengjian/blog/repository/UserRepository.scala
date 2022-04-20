package com.fengjian.blog.repository

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.model.{QuestionPO, UserBasicInfoPO, UserPO}
import com.fengjian.blog.router.model.user.{RetrievePasswordDTO, QuestionDTO}

trait UserRepository {

  def getUser(id: Int): IO[Either[UserNotFoundError.type , UserPO]]

  def login(username: String, password: String): IO[Either[UserNotFoundError.type , UserPO]]

  def register(user: UserPO): IO[Either[UserHasExistError.type , Unit]]

  def updateUserInfo(userId: Int, nickname: String): IO[Either[UserNotFoundError.type , String]]

  def checkUserInfo(retrievePasswordInfo: RetrievePasswordDTO): IO[Either[UserNotFoundError.type, UserBasicInfoPO]]

  def getUserQuestions(userId: Int): IO[List[QuestionDTO]]

}
