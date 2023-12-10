package com.lastpro.taskmate.network
import com.lastpro.taskmate.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
interface ApiService {
    @GET("user/{id}")
    suspend fun getUser(@Path("id") userId: Int): Response<User>

    @GET("user")
    suspend fun getAllUser(): Response<List<User>>

    @POST("user")
    suspend fun createUser(@Body user: User): Response<User>
}