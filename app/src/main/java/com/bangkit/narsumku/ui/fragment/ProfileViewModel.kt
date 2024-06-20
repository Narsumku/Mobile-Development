package com.bangkit.narsumku.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.narsumku.data.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel()  {

    fun getUserSession() = userRepository.getSession().asLiveData()
}