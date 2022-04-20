package com.fengjian.blog.repository.impl

import cats.effect.{IO, Resource}
import com.fengjian.blog.exception.{BlogNotFoundError, CommentNotFoundError}
import com.fengjian.blog.repository.CommentsRepository
import com.fengjian.blog.repository.model.{BlogPO, CommentPO}
import doobie.hikari.HikariTransactor
import doobie.implicits._

class CommentsRepositoryImpl(transactor: Resource[IO, HikariTransactor[IO]]) extends CommentsRepository{

  override def addComment(comment: CommentPO): IO[Int] = {
    transactor.use(
      xa => sql"insert into comments (comment, blog_id, user_id) values (${comment.comment}, ${comment.blogId}, ${comment.userId})"
        .update
        .withUniqueGeneratedKeys[Int]("id")
        .transact(xa)
    )

  }

  override def deleteComment(id: Int): IO[Int] = {
    transactor.use(
    xa => sql"delete from comments where id = $id"
      .update
      .run
      .transact(xa))
  }

  override def getBlogCommentList(blogId: Int, startNum: Int, pageSize: Int): IO[List[CommentPO]] = {
    transactor.use(
      xa => sql"select id,comment,blog_id,user_id from comments where blog_id = $blogId order by id limit $startNum,$pageSize"
        .query[CommentPO]
        .to[List]
        .transact(xa)
    )

  }

  override def updateCommentInfo(comment: CommentPO): IO[Either[CommentNotFoundError.type, Unit]] = {
    transactor.use(
      xa => sql"update comments set comment = ${comment.comment} where id = ${comment.id}"
        .update
        .run
        .transact(xa)
        .map {
          effectRaws => if (effectRaws == 1) Right(()) else Left(CommentNotFoundError)
        }
    )

  }
}
