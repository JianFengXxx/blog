package com.fengjian.blog.service

import com.fengjian.blog.config.DataSourceConfig
import org.flywaydb.core.Flyway

object FlywayService {

  def migrate(dbConfig: DataSourceConfig): Unit = {
    val flyway = Flyway
      .configure()
      .dataSource(dbConfig.jdbcUrl, dbConfig.username, dbConfig.password)
      .load

    flyway.migrate()
  }

}
