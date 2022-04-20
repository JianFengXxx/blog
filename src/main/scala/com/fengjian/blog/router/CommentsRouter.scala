package com.fengjian.blog.router

import cats.effect.IO
import com.fengjian.blog.router.model.comment.{CommentCreateDTO, CommentUpdateDTO}
import com.fengjian.blog.service.CommentsService
import io.circe.Decoder
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe.jsonOf
import org.http4s.{EntityDecoder, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import common._

class CommentsRouter(service: CommentsService) extends Http4sDsl[IO] {

  def routes(): HttpRoutes[IO] = {

    HttpRoutes.of[IO] {
      case req@POST -> Root / "comments" =>
        for {
          commentCreateDTO <- req.as[CommentCreateDTO]
          id <- service.addComment(commentCreateDTO)
          resp <- Ok(s"id: $id")
        } yield resp

      case DELETE -> Root / "comments" / IntVar(id) =>
        for {
          result <- service.deleteComment(id)
          resp <- Ok("comments has been deleted.")
        } yield resp

      case GET -> Root / "comments" :? PageNumQueryParamMatcher(pageNum) +& PageSizeQueryParamMatcher(pageSize) +& BlogIdQueryParamMatcher(blogId) =>
        for {
          commentList <- service.getBlogCommentList(blogId, pageSize, pageNum)
          resp <- Ok(commentList.asJson.noSpaces)
        } yield resp

      case req@PUT -> Root / "comments" =>
        for {
          commentUpdateDTO <- req.as[CommentUpdateDTO]
          result <- service.updateCommentInfo(commentUpdateDTO)
          resp <- result match {
            case Left(_) => BadRequest("comments not found. please check it")
            case Right(_) => Ok("update success")
          }
        } yield resp
    }
  }

  implicit def entityIODecoder[A <: Product : Decoder]: EntityDecoder[IO, A] = jsonOf[IO, A]

}
