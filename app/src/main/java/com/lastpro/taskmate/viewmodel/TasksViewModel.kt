package com.lastpro.taskmate.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lastpro.taskmate.model.ApiResponse
import com.lastpro.taskmate.model.LoginResponse
import com.lastpro.taskmate.model.Task
import com.lastpro.taskmate.model.TaskLabel
import com.lastpro.taskmate.model.User
import com.lastpro.taskmate.network.ApiClient
import com.lastpro.taskmate.network.ApiService
import kotlinx.coroutines.launch
import org.json.JSONObject

class TasksViewModel : ViewModel() {

    private val _tasksResponse = MutableLiveData<List<Task>>()
    val tasksResponse: LiveData<List<Task>> get() = _tasksResponse

    fun getTasks (context: Context,tasklabel_id: Int){
        viewModelScope.launch {
            val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val token   = preferences.getString("token","")
            try {
                val service : ApiService = ApiClient.apiService(token)
                val response = service.getTasks(tasklabel_id)
                if(response.isSuccessful){
                    val body = response.body() as List<Task>
                    _tasksResponse.value = body
                }else{
                    val message     = response.errorBody()!!.string()
                    Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getByIdTask (context: Context,id: Int, data : (Task)->Unit){
        viewModelScope.launch {
            val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val token   = preferences.getString("token","")
            try {
                val service : ApiService = ApiClient.apiService(token)
                val response = service.getByIdTask(id)

                if(response.isSuccessful){
                    val body = response.body() as Task
                    data(body)
                }else{
                    val message     = response.errorBody()!!.string()
                    Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun insertTask (context: Context, data : Task, onSuccess : (Boolean)->Unit){
        viewModelScope.launch {
            val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val token   = preferences.getString("token","")
            try {
                val service : ApiService = ApiClient.apiService(token)
                val response = service.insertTask(data)

                if(response.isSuccessful){
                    val body = response.body() as ApiResponse
                    Toast.makeText(context, body.message, Toast.LENGTH_SHORT).show()
                    onSuccess(true)
                }else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message     = jObjError.getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    onSuccess(false)
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                onSuccess(false)
            }
        }
    }

    fun updateTask (context: Context, data : Task, onSuccess : (Boolean)->Unit){
        viewModelScope.launch {
            val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val token   = preferences.getString("token","")
            try {
                val service : ApiService = ApiClient.apiService(token)
                val response = service.updateTask(data.id,data)

                if(response.isSuccessful){
                    val body = response.body() as ApiResponse
                    Toast.makeText(context, body.message, Toast.LENGTH_SHORT).show()
                    onSuccess(true)
                }else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message     = jObjError.getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    onSuccess(false)
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                onSuccess(false)
            }
        }
    }

    fun deleteTask (context: Context, id : Int,onSuccess : (Boolean)->Unit){
        viewModelScope.launch {
            val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val token   = preferences.getString("token","")
            try {
                val service : ApiService = ApiClient.apiService(token)
                val response = service.deleteTask(id)

                if(response.isSuccessful){
                    val body = response.body() as ApiResponse
                    Toast.makeText(context, body.message, Toast.LENGTH_SHORT).show()
                    onSuccess(true);
                }else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message     = jObjError.getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
