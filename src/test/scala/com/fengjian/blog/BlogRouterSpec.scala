package com.fengjian.blog

import cats.effect.IO
import com.fengjian.blog.exception.CommentNotFoundError
import com.fengjian.blog.mock.normal.MockBlogRepositoryNormal
import com.fengjian.blog.repository.{BlogRepository, CommentsRepository}
import com.fengjian.blog.repository.model.{BlogPO, CommentPO}
import com.fengjian.blog.router.BlogRouter
import com.fengjian.blog.router.model.request.blog.BlogCreateDTO
import com.fengjian.blog.service.impl.{BlogServiceImpl, CommentsServiceImpl}
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.implicits.{http4sKleisliResponseSyntaxOptionT, http4sLiteralsSyntax}
import org.specs2.mutable.Specification
import org.http4s.{Method, Request, Response, Status}
import io.circe.generic.auto._

class BlogRouterSpec extends Specification {

  "BlogRouter Test" should {
    "blog add success" in {
      val blogCreateBody = BlogCreateDTO("test", "test", 1)
      val blogCreateRequest = Request[IO](Method.POST, uri"/blog").withEntity(blogCreateBody)
      val mockBlogRepositoryNormal = new MockBlogRepositoryNormal()

      val blogCreateResponse = blogHttpClient(blogCreateRequest, mockBlogRepositoryNormal)
      blogCreateResponse.status must beEqualTo(Status.Ok)
    }
    "blog delete success" in {
      val blogDeleteRequest = Request[IO](Method.DELETE, uri"/blog/1")
      val mockBlogRepositoryNormal = new MockBlogRepositoryNormal()

      val blogDeleteResponse = blogHttpClient(blogDeleteRequest, mockBlogRepositoryNormal)
      blogDeleteResponse.status must beEqualTo(Status.Ok)
    }
  }

  private def blogHttpClient(request: Request[IO], blogRepository: BlogRepository): Response[IO] = {
    val response: Response[IO] = new BlogRouter(new BlogServiceImpl(blogRepository, new CommentsServiceImpl(new MockCommentsRepository()))).routes()
      .orNotFound(request)
      .unsafeRunSync()
    response
  }

  class MockCommentsRepository extends CommentsRepository{
    override def addComment(comment: CommentPO): IO[Int] = ???

    override def deleteComment(id: Int): IO[Int] = ???

    override def getBlogCommentList(blogId: Int, startNum: Int, pageSize: Int): IO[List[CommentPO]] = ???

    override def updateCommentInfo(comment: CommentPO): IO[Either[CommentNotFoundError.type, Unit]] = ???
  }

}
