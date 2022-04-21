package com.fengjian.blog.router.model.request.user

case class RetrievePasswordDTO(username: String, questions: List[QuestionDTO])
