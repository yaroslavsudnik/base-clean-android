package dev.sudnik.data.user

import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("/user")
    suspend fun users(
        @Query("group_id") groupId: String
    ): Response<UsersDTO>
}
