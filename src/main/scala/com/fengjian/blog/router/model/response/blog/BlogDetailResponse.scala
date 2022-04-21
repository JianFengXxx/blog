package com.fengjian.blog.router.model.response.blog

import com.fengjian.blog.repository.model.CommentPO

case class BlogDetailResponse(id: Int, title: String, content: String, authorId: Int, comments: List[CommentPO])
