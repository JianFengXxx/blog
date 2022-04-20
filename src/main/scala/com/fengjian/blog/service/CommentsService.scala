package com.fengjian.blog.service

import cats.effect.IO
import com.fengjian.blog.exception.{BlogNotFoundError, CommentNotFoundError}
import com.fengjian.blog.repository.model.{BlogPO, CommentPO}
import com.fengjian.blog.router.model.comment.{CommentCreateDTO, CommentUpdateDTO}

trait CommentsService {

  def deleteComment(id: Int): IO[Int]

  def addComment(commentCreateDTO: CommentCreateDTO): IO[Int]

  def getBlogCommentList(blogId: Int, pageSize: Int, pageNum: Int): IO[List[CommentPO]]

  def updateCommentInfo(commentUpdateDTO: CommentUpdateDTO): IO[Either[CommentNotFoundError.type, Unit]]

}
