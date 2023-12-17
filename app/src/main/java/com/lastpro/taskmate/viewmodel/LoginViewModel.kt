package com.lastpro.taskmate.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lastpro.taskmate.MainActivity
import com.lastpro.taskmate.login
import com.lastpro.taskmate.model.LoginRequest
import com.lastpro.taskmate.model.LoginResponse
import com.lastpro.taskmate.network.ApiClient
import com.lastpro.taskmate.network.ApiService
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Callback
import java.security.AccessController.getContext


class LoginViewModel : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    fun login(username: String, password: String,onSuccess: (LoginResponse)->Unit,onError: (String)->Unit) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(username, password)
                val service : ApiService = ApiClient.apiService
                val response = service.login(loginRequest)

                if(response.isSuccessful){
                    val data = response.body() as LoginResponse
                    onSuccess(data)
                    /*Log.d("DEbug login",data.message)*/
                }else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message     = jObjError.getString("message")
                    onError(message)

                }
            } catch (e: Exception) {
                onError(e.toString())
                // Tangani kesalahan jaringan atau kesalahan lainnya
                /*Log.e("DEbug Login","Not Connect");
                Log.e("DEbug Login",e.toString());
                e.printStackTrace()*/
            }
        }
    }
}
