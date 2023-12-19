package com.lastpro.taskmate.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lastpro.taskmate.MainActivity
import com.lastpro.taskmate.login
import com.lastpro.taskmate.model.LoginRequest
import com.lastpro.taskmate.model.LoginResponse
import com.lastpro.taskmate.model.User
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

    fun checkLogin(context: Context,isLogin : (Boolean)->Unit){
        val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        viewModelScope.launch {
            if(preferences.getBoolean("is_login",false)) {
                val myEdit: SharedPreferences.Editor = preferences.edit()
                try {
                    val token   = preferences.getString("token","")
                    val service : ApiService = ApiClient.apiService(token)
                    val response = service.getUserLogin()

                    if(response.isSuccessful){
                        val data = response.body() as User
                        isLogin(true)
                    }else{
                        myEdit.putString("token","")
                        myEdit.putBoolean("is_login",false)
                        myEdit.apply()
                        isLogin(false)
                    }
                } catch (e: Exception) {
                    myEdit.putString("token","")
                    myEdit.putBoolean("is_login",false)
                    myEdit.apply()
                    isLogin(false)
                }
            }else{
                isLogin(false)
            }
        }

    }
}
