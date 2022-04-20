package com.fengjian.blog.router.model.blog

import com.fengjian.blog.repository.model.BlogPO

case class BlogUpdateDTO(id: Int, title: String, content: String) {

  def convert2PO(): BlogPO = BlogPO(Option(id), title, content, 0)

}
