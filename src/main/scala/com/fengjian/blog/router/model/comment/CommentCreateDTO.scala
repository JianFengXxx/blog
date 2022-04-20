package com.fengjian.blog.router.model.comment

import com.fengjian.blog.repository.model.CommentPO

case class CommentCreateDTO(comment: String, blogId: Int, userId: Int) {

  def convert2PO(): CommentPO = CommentPO(null, comment, blogId, userId)

}
