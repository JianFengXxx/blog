package com.fengjian.blog.router.model.user

import com.fengjian.blog.repository.model.UserPO


case class UserCreateDTO(username: String, password: String, nickname: String) {

  def convert2PO(): UserPO = UserPO(None, username, password, nickname)
}