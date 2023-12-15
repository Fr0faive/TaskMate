package com.lastpro.taskmate.viewmodel

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    private val apiService = MyApplication.instance.apiService

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(username, password)
                val response = apiService.login(loginRequest)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _loginResponse.value = response.body()
                        // Tangani login berhasil
                    } else {
                        // Tangani login gagal
                    }
                }
            } catch (e: Exception) {
                // Tangani kesalahan jaringan atau kesalahan lainnya
            }
        }
    }
}
