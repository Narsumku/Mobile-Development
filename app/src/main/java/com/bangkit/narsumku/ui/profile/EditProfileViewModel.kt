package com.bangkit.narsumku.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.UpdateUserResponse

class EditProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun updateUser(userId: String, username: String, email: String, password: String): Results<UpdateUserResponse> {
        return userRepository.updateUser(userId, username, email, password)
    }

    fun getUserSession() = userRepository.getSession().asLiveData()
}