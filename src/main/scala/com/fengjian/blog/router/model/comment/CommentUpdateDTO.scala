package com.fengjian.blog.router.model.comment

import com.fengjian.blog.repository.model.CommentPO

case class CommentUpdateDTO(id: Int, comment: String, blogId: Int, userId: Int) {

  def convert2PO(): CommentPO = CommentPO(Option(id), comment, blogId, userId)

}
