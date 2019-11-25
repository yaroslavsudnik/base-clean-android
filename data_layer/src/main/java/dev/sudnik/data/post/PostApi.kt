package dev.sudnik.data.post

import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface PostApi {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("/post")
    suspend fun post(
        @Query("post_id") postId: String
    ): Response<PostDTO>
}
