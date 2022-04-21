package com.fengjian.blog.router

import cats.effect.IO
import com.fengjian.blog.router.model.request.blog.{BlogCreateDTO, BlogUpdateDTO}
import com.fengjian.blog.service.impl.BlogServiceImpl
import io.circe.Decoder
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe.jsonOf
import org.http4s.{EntityDecoder, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import common._

class BlogRouter(blogService: BlogServiceImpl) extends Http4sDsl[IO]{

  def routes(): HttpRoutes[IO] = {

    HttpRoutes.of[IO] {
      case req@POST -> Root / "blog" =>
        for {
          blogCreateDTO <- req.as[BlogCreateDTO]
          id <- blogService.addBlog(blogCreateDTO)
          resp <- Ok(s"id: $id")
        } yield resp

      case DELETE -> Root / "blog" / IntVar(id) =>
        for {
          result <- blogService.deleteBlog(id)
          resp <- Ok("Blog has been deleted.")
        } yield resp

      case GET -> Root / "blog" :? PageNumQueryParamMatcher(pageNum) +& PageSizeQueryParamMatcher(pageSize) =>
        for {
          blogList <- blogService.getBlogList(pageSize, pageNum)
          resp <- Ok(blogList.asJson.noSpaces)
        } yield resp

      case req@PUT -> Root / "blog" =>
        for {
          blogUpdateDTO <- req.as[BlogUpdateDTO]
          result <- blogService.updateBlogInfo(blogUpdateDTO)
          resp <- result match {
            case Left(_) => BadRequest("Blog not found. please check it")
            case Right(_) => Ok("update success")
          }
        } yield resp

      case GET -> Root / "blog" / IntVar(id) =>
        for {
          blogDetail <- blogService.getBlogInfo(id)
          resp <- blogDetail match {
            case Left(_) => BadRequest("Blog not found. please check it")
            case Right(s) => Ok(s.asJson.noSpaces)
          }
        } yield resp
    }
  }

  implicit def entityIODecoder[A <: Product : Decoder]: EntityDecoder[IO, A] = jsonOf[IO, A]

}
