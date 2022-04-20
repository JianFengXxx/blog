package com.fengjian.blog

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.fengjian.blog.common.EnvConfigCommon
import com.fengjian.blog.config.DataSourceConfig
import com.fengjian.blog.service.FlywayService

object Main extends IOApp {

  def run(args: List[String]) = {

    val dbConfig = DataSourceConfig(EnvConfigCommon.getEnvConfig())
    FlywayService.migrate(dbConfig)

    BlogServer.stream.compile.drain.as(ExitCode.Success)
  }

}