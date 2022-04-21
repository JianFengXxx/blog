package com.fengjian.blog.repository.impl

import cats.effect.{IO, Resource}
import com.fengjian.blog.exception._
import com.fengjian.blog.repository.{DatabaseTransactor, UserRepository}
import com.fengjian.blog.repository.model.{QuestionPO, UserBasicInfoPO, UserPO}
import com.fengjian.blog.router.model.user.{RetrievePasswordDTO, QuestionDTO}
import doobie.hikari.HikariTransactor
import doobie.implicits._



class UserRepositoryImpl(transactor: Resource[IO, HikariTransactor[IO]]) extends UserRepository {

  def getUser(id: Int): IO[Either[UserNotFoundError.type , UserPO]] = {
    transactor.use(
      xa => sql"SELECT id, username, password, nickname FROM user_info where id = $id"
        .query[UserPO]
        .option
        .transact(xa)
        .map {
          case Some(s) => Right(s)
          case _ => Left(UserNotFoundError)
        }
    )
  }

  def login(username: String, password: String): IO[Either[UserNotFoundError.type , UserPO]] = {
    transactor.use(
      xa => sql"SELECT id, username, password, nickname FROM user_info where username = $username and password = $password"
        .query[UserPO]
        .option
        .transact(xa)
        .map {
          case Some(s) => Right(s)
          case _ => Left(UserNotFoundError)
        }
    )
  }

  def register(user: UserPO): IO[Either[UserHasExistError.type , Unit]] = {
    transactor.use(
      xa => sql"insert into user_info (username, password, nickname) values (${user.username}, ${user.password}, ${user.nickname})"
        .update
        .run
        .transact(xa)
        .attemptSql
        .map {
          case Left(_) => Left(UserHasExistError)
          case Right(_) => Right(())
        }
    )
  }

  def updateUserInfo(userId: Int, nickname: String): IO[Either[UserNotFoundError.type , String]] = {
    transactor.use(
      xa => sql"update user_info set nickname = $nickname where id = $userId"
        .update
        .run
        .transact(xa)
        .map{
          affectRows => if (affectRows == 1) Right(nickname) else Left(UserNotFoundError)
        }
    )
  }

  override def getUserBasicInfo(username: String): IO[Either[UserNotFoundError.type, UserBasicInfoPO]] = {
    transactor.use(
      xa => {
        sql"select id, username, password, nickname, name, birthday, gender from user_info where username=$username"
          .query[UserBasicInfoPO]
          .option
          .transact(xa)
          .map {
            case Some(userBasicInfoPO) => Right(userBasicInfoPO)
            case _ => Left(UserNotFoundError)
          }
      }
    )
  }

  override def getUserQuestions(userId: Int): IO[List[QuestionDTO]] = {
    transactor.use(
      xa => sql"select question,answer from user_questions where user_id = $userId"
        .query[QuestionDTO]
        .to[List]
        .transact(xa)
    )
  }

}
