package com.lastpro.taskmate.network
import com.lastpro.taskmate.model.ApiResponse
import com.lastpro.taskmate.model.LoginRequest
import com.lastpro.taskmate.model.LoginResponse
import com.lastpro.taskmate.model.Task
import com.lastpro.taskmate.model.TaskLabel
import com.lastpro.taskmate.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
interface ApiService {
    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>

    @GET("user")
    suspend fun getAllUser(): Response<List<User>>

    @POST("user")
    suspend fun createUser(@Body user: User): Response<User>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("user_login")
    suspend fun getUserLogin(): Response<User>

    @GET("logout")
    suspend fun logout(): Response<ApiResponse>

    @GET("tasklabel/get")
    suspend fun getTaskLabel(): Response<List<TaskLabel>>
    @GET("tasklabel/get_by/{id}")
    suspend fun getByIdTaskLabel(@Path("id") id: Int): Response<TaskLabel>
    @POST("tasklabel/insert")
    suspend fun insertTaskLabel(@Body taskLabel: TaskLabel): Response<ApiResponse>
    @POST("tasklabel/update/{id}")
    suspend fun updateTaskLabel(@Path("id") id: Int,@Body taskLabel: TaskLabel): Response<ApiResponse>
    @POST("tasklabel/delete/{id}")
    suspend fun deleteTaskLabel(@Path("id") id: Int): Response<ApiResponse>

    @GET("tasks/get/{tasklabel_id}")
    suspend fun getTasks(@Path("tasklabel_id") id: Int): Response<List<Task>>
    @GET("tasks/get_by/{id}")
    suspend fun getByIdTask(@Path("id") id: Int): Response<Task>
    @POST("tasks/insert")
    suspend fun insertTask(@Body task: Task): Response<ApiResponse>
    @POST("tasks/update/{id}")
    suspend fun updateTask(@Path("id") id: Int,@Body task: Task): Response<ApiResponse>
    @POST("tasks/delete/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<ApiResponse>

}
