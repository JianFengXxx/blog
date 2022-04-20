package com.fengjian.blog.service.impl

import cats.effect.IO
import com.fengjian.blog.exception.CommentNotFoundError
import com.fengjian.blog.repository.CommentsRepository
import com.fengjian.blog.repository.model.{BlogPO, CommentPO}
import com.fengjian.blog.router.model.comment.{CommentCreateDTO, CommentUpdateDTO}
import com.fengjian.blog.service.CommentsService

class CommentsServiceImpl(repository: CommentsRepository) extends CommentsService{

  override def deleteComment(id: Int): IO[Int] = {
    repository.deleteComment(id)
  }

  override def addComment(commentCreateDTO: CommentCreateDTO): IO[Int] = {
    val commentPO = commentCreateDTO.convert2PO()
    repository.addComment(commentPO)
  }

  override def getBlogCommentList(blogId: Int, pageSize: Int, pageNum: Int): IO[List[CommentPO]] = {
    val startNum = (pageNum - 1) * pageSize
    repository.getBlogCommentList(blogId, startNum, pageSize)
  }

  override def updateCommentInfo(commentUpdateDTO: CommentUpdateDTO): IO[Either[CommentNotFoundError.type, Unit]] = {
    val commentPO = commentUpdateDTO.convert2PO()
    repository.updateCommentInfo(commentPO)
  }
}
