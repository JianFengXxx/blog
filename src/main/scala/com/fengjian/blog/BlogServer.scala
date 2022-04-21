package com.fengjian.blog

import cats.MonoidK.ops.toAllMonoidKOps
import cats.effect.{ConcurrentEffect, ContextShift, IO, Timer}
import com.fengjian.blog.common.EnvConfigCommon
import com.fengjian.blog.repository.DatabaseTransactor
import com.fengjian.blog.router.{BlogRouter, CommentsRouter, UserRouter}
import com.fengjian.blog.repository.impl.{BlogRepositoryImpl, CommentsRepositoryImpl, UserRepositoryImpl}
import com.fengjian.blog.service.impl.{BlogServiceImpl, CommentsServiceImpl, UserServiceImpl}
import fs2.Stream
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger

import scala.concurrent.ExecutionContext
import org.http4s.implicits._
import config.DataSourceConfig

object BlogServer {

  def stream(implicit T: Timer[IO], C: ContextShift[IO], CE: ConcurrentEffect[IO]): Stream[IO, Nothing] = {
    for {
      client <- BlazeClientBuilder[IO](ExecutionContext.global).stream

      config = EnvConfigCommon.getEnvConfig()
      transactor = DatabaseTransactor.transactor(C, DataSourceConfig(config))
      userRepository = new UserRepositoryImpl(transactor)
      blogRepository = new BlogRepositoryImpl(transactor)
      commentsRepository = new CommentsRepositoryImpl(transactor)

      userService = new UserServiceImpl(userRepository)
      commentsService = new CommentsServiceImpl(commentsRepository)
      blogService = new BlogServiceImpl(blogRepository, commentsService)

      userRouter = new UserRouter(userService)
      blogRouter = new BlogRouter(blogService)
      commentsRouter = new CommentsRouter(commentsService)

      httpApp = (
        userRouter.routes() <+>
        blogRouter.routes() <+>
        commentsRouter.routes()
      ).orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- BlazeServerBuilder[IO]
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(finalHttpApp)
        .serve
    } yield exitCode
  }.drain

}
