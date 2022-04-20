package com.fengjian.blog.service.model

import com.fengjian.blog.repository.model.CommentPO

case class BlogDetailDTO(id: Int, title: String, content: String, authorId: Int, comments: List[CommentPO])
