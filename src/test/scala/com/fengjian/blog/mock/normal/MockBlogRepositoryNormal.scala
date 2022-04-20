package com.fengjian.blog.mock.normal

import cats.effect.IO
import com.fengjian.blog.exception.BlogNotFoundError
import com.fengjian.blog.repository.BlogRepository
import com.fengjian.blog.repository.model.BlogPO

class MockBlogRepositoryNormal extends BlogRepository{

  override def addBlog(blog: BlogPO): IO[Int] = {
    IO.pure(1)
  }

  override def deleteBlog(id: Int): IO[Int] = {
    IO.pure(1)
  }

  override def getBlogList(startNum: Int, pageSize: Int): IO[List[BlogPO]] = ???

  override def updateBlogInfo(blogPO: BlogPO): IO[Either[BlogNotFoundError.type, Unit]] = ???

  override def getBlogInfo(id: Int): IO[Either[BlogNotFoundError.type, BlogPO]] = ???
}
