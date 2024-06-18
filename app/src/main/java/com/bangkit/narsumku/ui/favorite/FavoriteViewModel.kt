package com.bangkit.narsumku.ui.favorite

import androidx.lifecycle.ViewModel
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.AddFavoriteResponse
import com.bangkit.narsumku.data.response.DeleteFavoriteResponse

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun addFavorite(userId: String, speakerId: String): Results<AddFavoriteResponse> {
        return userRepository.addFavorite(userId, speakerId)
    }

    suspend fun deleteFavorite(userId: String, speakerId: String): Results<DeleteFavoriteResponse> {
        return userRepository.deleteFavorite(userId, speakerId)
    }
}