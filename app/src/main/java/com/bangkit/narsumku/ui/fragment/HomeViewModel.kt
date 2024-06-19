package com.bangkit.narsumku.ui.fragment

import androidx.lifecycle.ViewModel
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.PopularSpeaker
import com.bangkit.narsumku.data.response.RecommendationSpeaker

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun getHomeForPopular(): Results<List<PopularSpeaker>> {
        return userRepository.getHomeForPopular()
    }

    suspend fun getHomeForRecommendation(): Results<List<RecommendationSpeaker>> {
        return userRepository.getHomeForRecommendation()
    }
}