package com.lastpro.taskmate.viewmodel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lastpro.taskmate.TasklabelEdit
import com.lastpro.taskmate.login
import com.lastpro.taskmate.model.ApiResponse
import com.lastpro.taskmate.model.LoginRequest
import com.lastpro.taskmate.model.LoginResponse
import com.lastpro.taskmate.model.User
import com.lastpro.taskmate.network.ApiClient
import com.lastpro.taskmate.network.ApiService
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel : ViewModel() {

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
                        /*myEdit.putString("token","")
                        myEdit.putBoolean("is_login",false)
                        myEdit.apply()*/
                        isLogin(false)
                        Log.e("Check Login",response.errorBody()!!.string().toString());
                    }
                } catch (e: Exception) {
                    /*myEdit.putString("token","")
                    myEdit.putBoolean("is_login",false)
                    myEdit.apply()*/
                    isLogin(false)
                    Log.e("Check Login",e.toString());
                }
            }else{
                isLogin(false)
            }
        }

    }

    fun logout(context: Context,onSuccess: (Boolean) -> Unit){
        val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        viewModelScope.launch {
            val myEdit: SharedPreferences.Editor = preferences.edit()
            try {
                val token   = preferences.getString("token","")
                val service : ApiService = ApiClient.apiService(token)
                val response = service.logout()

                if(response.isSuccessful){
                    val data = response.body() as ApiResponse
                    myEdit.putString("token","")
                    myEdit.putBoolean("is_login",false)
                    myEdit.apply()
                    onSuccess(true)
                }else{
                    Log.e("Check Logout",response.errorBody()!!.string().toString());
                }
            } catch (e: Exception) {
                Log.e("Check Logout",e.toString());
            }
        }

    }
}
