package com.fengjian.blog.router.model.user

case class RetrievePasswordDTO(username: String, name: String, birthday: String, gender: Int, questions: List[QuestionDTO])
