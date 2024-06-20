package com.bangkit.narsumku.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.DeleteUserResponse
import com.bangkit.narsumku.data.response.GetUserResponse
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel()  {

    fun getUserSession() = userRepository.getSession().asLiveData()

    suspend fun getUser(userId: String): Results<GetUserResponse> {
        return userRepository.getUser(userId)
    }

    suspend fun deleteUser(userId: String): Results<DeleteUserResponse> {
        return userRepository.deleteUser(userId)
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}