package com.fengjian.blog.repository.model

case class CommentPO(id: Option[Int], comment: String, blogId: Int, userId: Int)
