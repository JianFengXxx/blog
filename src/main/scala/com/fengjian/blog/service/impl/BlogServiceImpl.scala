package com.fengjian.blog.service.impl

import cats.effect.IO
import com.fengjian.blog.exception.BlogNotFoundError
import com.fengjian.blog.repository.BlogRepository
import com.fengjian.blog.repository.model.{BlogPO, CommentPO}
import com.fengjian.blog.router.model.request.blog.{BlogCreateDTO, BlogUpdateDTO}
import com.fengjian.blog.router.model.response.blog.BlogDetailResponse
import com.fengjian.blog.service.{BlogService, CommentsService}

class BlogServiceImpl(repository: BlogRepository, commentsService: CommentsService) extends BlogService {


  def deleteBlog(id: Int): IO[Int] = {
    repository.deleteBlog(id)
  }

  def addBlog(blogCreateDTO: BlogCreateDTO): IO[Int] = {
    val blogPO: BlogPO = blogCreateDTO.convert2PO()
    repository.addBlog(blogPO)
  }

  override def getBlogList(pageSize: Int, pageNum: Int): IO[List[BlogPO]] = {
    val startNum = (pageNum - 1) * pageSize
    repository.getBlogList(startNum, pageSize)
  }

  override def updateBlogInfo(blogUpdateDTO: BlogUpdateDTO): IO[Either[BlogNotFoundError.type, Unit]] = {
    val blogPO = blogUpdateDTO.convert2PO()
    repository.updateBlogInfo(blogPO)
  }

  override def getBlogInfo(id: Int): IO[Either[BlogNotFoundError.type, BlogDetailResponse]] = {
    val blogInfo: IO[Either[BlogNotFoundError.type, BlogPO]] = repository.getBlogInfo(id)
    blogInfo.unsafeRunSync() match {
      case Right(bl) => {
        val comments: IO[List[CommentPO]] = commentsService.getBlogCommentList(id, 20, 1)
        val blogDetailDTO = BlogDetailResponse(bl.id.get, bl.title, bl.content, bl.authorId, comments.unsafeRunSync())
        IO.pure(Right(blogDetailDTO))
      }
      case Left(_) => IO.pure(Left(BlogNotFoundError))
    }
  }
}
