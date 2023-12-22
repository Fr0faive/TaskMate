package com.lastpro.taskmate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lastpro.taskmate.model.LoginRequest
import com.lastpro.taskmate.model.LoginResponse
import com.lastpro.taskmate.network.ApiClient
import com.lastpro.taskmate.network.ApiService
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterViewModel : ViewModel() {
    fun register(username: String, password: String, onSuccess: (LoginResponse)->Unit, onError: (String)->Unit) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(username, password)
                val service : ApiService = ApiClient.apiService
                val response = service.register(loginRequest)

                if(response.isSuccessful){
                    val data = response.body() as LoginResponse
                    onSuccess(data)
                    /*Log.d("DEbug login",data.message)*/
                }else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message     = jObjError.getString("message")
//                    Log.e("DEbug Login",response.errorBody()!!.string().toString());
                    onError(message)
                }
            } catch (e: Exception) {
                onError(e.toString())
                // Tangani kesalahan jaringan atau kesalahan lainnya
                /*Log.e("DEbug Login",e.toString());
                e.printStackTrace()*/
            }
        }
    }
}
