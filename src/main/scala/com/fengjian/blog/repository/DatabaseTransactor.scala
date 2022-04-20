package com.fengjian.blog.repository

import cats.effect.{Blocker, ContextShift, IO, Resource}
import com.fengjian.blog.config.DataSourceConfig
import doobie.Transactor
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts

object DatabaseTransactor {

  def transactor(implicit cs: ContextShift[IO], dbConfig: DataSourceConfig): Resource[IO, HikariTransactor[IO]] = {
    for {
      ce <- ExecutionContexts.fixedThreadPool[IO](Runtime.getRuntime.availableProcessors() * 2)
      be <- Blocker[IO]
      xa <- HikariTransactor.newHikariTransactor[IO](
        dbConfig.driver,
        dbConfig.jdbcUrl,
        dbConfig.username,
        dbConfig.password,
        ce,
        be
      )
    } yield xa
  }

}
