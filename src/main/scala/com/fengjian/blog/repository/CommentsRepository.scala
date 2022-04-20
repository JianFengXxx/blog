package com.fengjian.blog.repository

import cats.effect.IO
import com.fengjian.blog.exception.{CommentNotFoundError}
import com.fengjian.blog.repository.model.{CommentPO}

trait CommentsRepository {

  def addComment(comment: CommentPO): IO[Int]

  def deleteComment(id: Int): IO[Int]

  def getBlogCommentList(blogId: Int, startNum: Int, pageSize: Int): IO[List[CommentPO]]

  def updateCommentInfo(comment: CommentPO): IO[Either[CommentNotFoundError.type, Unit]]

}
