package com.fengjian.blog.repository

import cats.effect.IO
import com.fengjian.blog.exception.BlogNotFoundError
import com.fengjian.blog.repository.model.BlogPO

trait BlogRepository {

  def addBlog(blog: BlogPO): IO[Int]

  def deleteBlog(id: Int): IO[Int]

  def getBlogList(startNum: Int, pageSize: Int): IO[List[BlogPO]]

  def updateBlogInfo(blogPO: BlogPO): IO[Either[BlogNotFoundError.type, Unit]]

  def getBlogInfo(id: Int): IO[Either[BlogNotFoundError.type , BlogPO]]

}
