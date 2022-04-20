package com.fengjian.blog.router

import org.http4s.dsl.impl.QueryParamDecoderMatcher

package object common {

  object PageSizeQueryParamMatcher extends QueryParamDecoderMatcher[Int]("pageSize")

  object PageNumQueryParamMatcher extends QueryParamDecoderMatcher[Int]("pageNum")

  object BlogIdQueryParamMatcher extends QueryParamDecoderMatcher[Int]("blogId")

}
