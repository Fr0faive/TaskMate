package com.lastpro.taskmate.network
import com.lastpro.taskmate.model.LoginRequest
import com.lastpro.taskmate.model.LoginResponse
import com.lastpro.taskmate.model.User
import retrofit2.Call
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

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("user_login")
    suspend fun getUserLogin(): Response<User>
}
