package com.fengjian.blog.router

import cats.effect.IO
import com.fengjian.blog.router.model.request.user.{RetrievePasswordDTO, UserCreateDTO, UserLoginDTO, UserUpdateDTO}
import com.fengjian.blog.service.UserService
import io.circe.{Decoder, Encoder}
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder, HttpRoutes, MediaType, Uri}
import org.http4s.dsl.Http4sDsl

class UserRouter(userService: UserService) extends Http4sDsl[IO] {

  def routes(): HttpRoutes[IO] = {

    HttpRoutes.of[IO] {
      case req@POST -> Root / "login" =>
        for {
          userLogin <- req.as[UserLoginDTO]
          result <- userService.login(userLogin)
          resp <- result match {
            case Left(_) => BadRequest("username or password is invalid. please try again.")
            case Right(result) => Ok(s"login success. userInfo: ${result.asJson.noSpaces}")
          }
        } yield resp


      case GET -> Root / "user" / IntVar(id) =>
        for {
          user <- userService.getUser(id)
          resp <- user match {
            case Left(_) => BadRequest("user not exist.")
            case Right(user) => Ok(user.asJson.noSpaces)
          }
        } yield resp

      case req@POST -> Root / "register" =>
        for {
          register <- req.as[UserCreateDTO]
          result <- userService.register(register)
          resp <- result match {
            case Left(_) => BadRequest("username has exist.")
            case Right(_) => Ok("register successful")
          }
        } yield resp

      case req@PUT -> Root / "user" =>
        for {
          userUpdateDTO <- req.as[UserUpdateDTO]
          result <- userService.updateUserInfo(userUpdateDTO)
          resp <- result match {
            case Left(_) => BadRequest("user not exist.")
            case Right(nickname) => Ok(nickname.asJson.noSpaces)
          }
        } yield resp

      case req@POST -> Root / "user" / "forgot" =>
        for {
          retrievePasswordDTO <- req.as[RetrievePasswordDTO]
          result <- userService.retrievePassword(retrievePasswordDTO)
          resp <- result match {
            case Left(_) => BadRequest("user not exist.")
            case Right(password) => Ok(s"password: $password")
          }
        } yield resp
    }
  }

  implicit def entityIODecoder[A <: Product : Decoder]: EntityDecoder[IO, A] = jsonOf[IO, A]

}
