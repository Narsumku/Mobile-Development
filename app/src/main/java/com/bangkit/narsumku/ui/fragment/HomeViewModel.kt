package com.bangkit.narsumku.ui.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.PopularSpeaker
import com.bangkit.narsumku.data.response.Speaker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun getHomeForPopular(): Results<List<PopularSpeaker>> {
        return userRepository.getHome()
    }
}