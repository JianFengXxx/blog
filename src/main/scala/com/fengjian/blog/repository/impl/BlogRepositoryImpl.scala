package com.fengjian.blog.repository.impl

import cats.effect.{IO, Resource}
import com.fengjian.blog.exception.BlogNotFoundError
import com.fengjian.blog.repository.BlogRepository
import com.fengjian.blog.repository.model.BlogPO
import doobie.hikari.HikariTransactor
import doobie.implicits._

class BlogRepositoryImpl(transactor: Resource[IO, HikariTransactor[IO]]) extends BlogRepository {

  def addBlog(blog: BlogPO): IO[Int] = {
    transactor.use(
      xa => sql"insert into blog (title, content, author_id) values (${blog.title}, ${blog.content}, ${blog.authorId})"
        .update
        .withUniqueGeneratedKeys[Int]("id")
        .transact(xa)
    )
  }

  def deleteBlog(id: Int): IO[Int] = {
    transactor.use(
      xa => sql"update blog set delete = 1 where id = $id"
        .update
        .run
        .transact(xa)
    )
  }

  def getBlogList(startNum: Int, pageSize: Int): IO[List[BlogPO]] = {
    transactor.use(
      xa => sql"select id,title,content,author_id from blog where `delete` = 0 order by id limit $startNum,$pageSize"
        .query[BlogPO]
        .to[List]
        .transact(xa)
    )
  }

  def updateBlogInfo(blogPO: BlogPO): IO[Either[BlogNotFoundError.type, Unit]] = {
    transactor.use(
      xa => sql"update blog set title = ${blogPO.title}, content = ${blogPO.content} where `delete` = 0 and id = ${blogPO.id}"
        .update
        .run
        .transact(xa)
        .map {
          effectRaws => if (effectRaws == 1) Right(()) else Left(BlogNotFoundError)
        }
    )
  }

  override def getBlogInfo(id: Int): IO[Either[BlogNotFoundError.type, BlogPO]] = {
    transactor.use(
      xa => sql"select id,title,content,author_id from blog where `delete` = 0 and id = $id"
        .query[BlogPO]
        .option
        .transact(xa)
        .map {
          case Some(s) => Right(s)
          case _ => Left(BlogNotFoundError)
        }
    )
  }
}
