package com.bangkit.narsumku.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Results<LoginResponse>>()
    val loginResult: LiveData<Results<LoginResponse>> get() = _loginResult

    fun login(email: String, password: String) {
        _loginResult.value = Results.Loading
        viewModelScope.launch {
            try {
                val result = userRepository.login(email, password)
                _loginResult.value = result
            } catch (e: Exception) {
                _loginResult.value = Results.Error(e.message ?: "An error occurred")
            }
        }
    }
}