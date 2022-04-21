package com.fengjian.blog

import cats.effect.IO
import com.fengjian.blog.router.UserRouter
import com.fengjian.blog.mock.abnormal.MockUserRepositoryAbnormal
import com.fengjian.blog.mock.normal.MockUserRepositoryNormal
import com.fengjian.blog.repository.UserRepository
import com.fengjian.blog.router.model.user.{UserCreateDTO, UserLoginDTO, UserUpdateDTO}
import com.fengjian.blog.service.impl.UserServiceImpl
import org.http4s.{Method, Request, Response, Status}
import org.http4s.implicits._
import org.specs2.mutable._
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder

class UserRouterSpec extends Specification {

  "UserRouter Test" should {
    "login success" in {
      val userLoginBody = UserLoginDTO("test", "test")
      val loginRequest = Request[IO](Method.POST, uri"/login").withEntity(userLoginBody)
      val mockUserRepositoryNormal = new MockUserRepositoryNormal()

      val loginResponse = userHttpClient(loginRequest, mockUserRepositoryNormal)

      loginResponse.status must beEqualTo(Status.Ok)
    }

    "login invalid user" in {
      val userLoginBody = UserLoginDTO("invalid", "invalid")
      val loginRequest = Request[IO](Method.POST, uri"/login").withEntity(userLoginBody)
      val mockUserRepositoryAbnormal = new MockUserRepositoryAbnormal()

      val loginResponse = userHttpClient(loginRequest, mockUserRepositoryAbnormal)

      val response = loginResponse.as[String].unsafeRunSync()

      response must beEqualTo("username or password is invalid. please try again.")
    }

    "user register success" in {
      val userCreateBody = UserCreateDTO("test", "test", "test", "test", "2000-01-01", 1)
      val registerRequest = Request[IO](Method.POST, uri"/register").withEntity(userCreateBody)

      val mockUserRepositoryNormal = new MockUserRepositoryNormal()
      val registerResponse = userHttpClient(registerRequest, mockUserRepositoryNormal)
      registerResponse.status must beEqualTo(Status.Ok)
    }

    "get user info success" in {
      val userInfoRequest: Request[IO] = Request[IO](Method.GET, uri"/user/1")
      val mockUserRepositoryNormal = new MockUserRepositoryNormal()

      val userInfoResponse = userHttpClient(userInfoRequest, mockUserRepositoryNormal)
      val result: IO[String] = userInfoResponse.as[String]
      val resultStr = result.unsafeRunSync()
      resultStr must beEqualTo("{\"id\":1,\"username\":\"test\",\"password\":\"test\",\"nickname\":\"test\"}")
    }

    "user info update success" in {
      val userUpdateBody = UserUpdateDTO(1, "xxx")
      val userUpdateRequest: Request[IO] = Request[IO](Method.PUT, uri"/user").withEntity(userUpdateBody)
      val mockUserRepositoryNormal = new MockUserRepositoryNormal()

      val userUpdateResponse = userHttpClient(userUpdateRequest, mockUserRepositoryNormal)
      userUpdateResponse.status must beEqualTo(Status.Ok)
    }
  }

  private def userHttpClient(request: Request[IO], userRepository: UserRepository): Response[IO] = {
    val response: Response[IO] = new UserRouter(new UserServiceImpl(userRepository)).routes()
      .orNotFound(request)
      .unsafeRunSync()
    response
  }

}
