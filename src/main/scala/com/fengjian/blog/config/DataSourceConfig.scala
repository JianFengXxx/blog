package com.fengjian.blog.config

import com.typesafe.config._

case class DataSourceConfig private(driver: String, jdbcUrl: String, username: String, password: String)

object DataSourceConfig {

  def apply(envConfig: Config): DataSourceConfig = {
    val dataSourceConfig: Config = envConfig.getConfig("datasource")
    DataSourceConfig(
      dataSourceConfig.getString("driver"),
      dataSourceConfig.getString("jdbcUrl"),
      dataSourceConfig.getString("username"),
      dataSourceConfig.getString("password")
    )
  }

}
