package com.fengjian.blog.router.model.request.blog

import com.fengjian.blog.repository.model.BlogPO

case class BlogCreateDTO(title: String, content: String, authorId: Int) {

  def convert2PO(): BlogPO = BlogPO(None, title, content, authorId)

}
