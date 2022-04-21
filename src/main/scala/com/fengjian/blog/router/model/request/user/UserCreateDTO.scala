package com.fengjian.blog.router.model.request.user

import com.fengjian.blog.repository.model.UserPO

import java.time.{LocalDate, LocalDateTime}


case class UserCreateDTO(username: String, password: String, nickname: String, name: String, birthday: String, gender: Int)
