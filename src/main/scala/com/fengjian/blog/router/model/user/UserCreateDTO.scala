package com.fengjian.blog.router.model.user

import com.fengjian.blog.repository.model.UserPO

import java.time.{LocalDate, LocalDateTime}


case class UserCreateDTO(username: String, password: String, nickname: String, name: String, birthday: String, gender: Int) {

  def convert2PO(): UserPO = {
    UserPO(None, username, password, nickname, name, LocalDate.parse(birthday), gender, LocalDateTime.now())
  }

}