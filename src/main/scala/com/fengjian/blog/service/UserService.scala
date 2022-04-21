package com.fengjian.blog.service

import cats.effect.IO
import com.fengjian.blog.exception.{UserHasExistError, UserNotFoundError}
import com.fengjian.blog.repository.model.UserPO
import com.fengjian.blog.router.model.user.{RetrievePasswordDTO, UserCreateDTO, UserLoginDTO, UserUpdateDTO}

trait UserService {

  def updateUserInfo(userUpdateDTO: UserUpdateDTO): IO[Either[UserNotFoundError.type, String]]

  def register(register: UserCreateDTO): IO[Either[UserHasExistError.type, Unit]]

  def getUser(id: Int): IO[Either[UserNotFoundError.type, UserPO]]

  def login(userLogin: UserLoginDTO): IO[Either[UserNotFoundError.type, UserPO]]

  def retrievePassword(retrievePasswordDTO: RetrievePasswordDTO): IO[Either[UserNotFoundError.type, String]]

}
