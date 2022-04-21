package com.fengjian.blog.service

import cats.effect.IO
import com.fengjian.blog.exception.BlogNotFoundError
import com.fengjian.blog.repository.model.BlogPO
import com.fengjian.blog.router.model.request.blog.{BlogCreateDTO, BlogUpdateDTO}
import com.fengjian.blog.router.model.response.blog.BlogDetailResponse

trait BlogService {

  def deleteBlog(id: Int): IO[Int]

  def addBlog(blogCreateDTO: BlogCreateDTO): IO[Int]

  def getBlogList(pageSize: Int, pageNum: Int): IO[List[BlogPO]]

  def updateBlogInfo(blogUpdateDTO: BlogUpdateDTO): IO[Either[BlogNotFoundError.type, Unit]]

  def getBlogInfo(id: Int): IO[Either[BlogNotFoundError.type , BlogDetailResponse]]

}
