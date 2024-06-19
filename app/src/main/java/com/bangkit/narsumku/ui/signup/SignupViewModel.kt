package com.bangkit.narsumku.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.SignupResponse
import kotlinx.coroutines.launch

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _registrationResult = MutableLiveData<Results<SignupResponse>>()
    val registrationResult: LiveData<Results<SignupResponse>> get() = _registrationResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _registrationResult.value = Results.Loading
                val result = userRepository.signup(name, email, password)
                _registrationResult.postValue(result)
            } catch (e: Exception) {
                _registrationResult.postValue(Results.Error("Terjadi kesalahan: ${e.message}"))
            }
        }
    }
}