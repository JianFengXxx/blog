package com.fengjian.blog.common

import com.typesafe.config.{Config, ConfigFactory}

object EnvConfigCommon {

  def getEnvConfig(): Config = ConfigFactory.load()

}
