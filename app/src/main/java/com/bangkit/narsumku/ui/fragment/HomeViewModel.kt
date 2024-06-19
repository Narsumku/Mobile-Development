package com.bangkit.narsumku.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.PopularSpeaker
import com.bangkit.narsumku.data.response.RecommendationSpeakerResponse

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun getHomeForPopular(): Results<List<PopularSpeaker>> {
        return userRepository.getHomeForPopular()
    }

    suspend fun getHomeForRecommendation(): Results<List<RecommendationSpeakerResponse>> {
        return userRepository.getHomeForRecommendation()
    }

    fun getUserSession() = userRepository.getSession().asLiveData()
}