package com.bangkit.narsumku.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.AddFavoriteResponse
import com.bangkit.narsumku.data.response.GetFavoriteResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _favorites = MutableStateFlow<List<GetFavoriteResponse>>(emptyList())
    val favorites: LiveData<List<GetFavoriteResponse>> = _favorites.asLiveData()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getFavorite(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true // Set status loading menjadi true
            userRepository.getFavorite(userId)
                .catch { e ->
                    _favorites.value = emptyList()
                    _isLoading.value = false // Set status loading menjadi false setelah selesai
                }
                .collect {
                    _favorites.value = it
                    _isLoading.value = false // Set status loading menjadi false setelah selesai
                }
        }
    }

    suspend fun addFavorite(userId: String, speakerId: String): Results<AddFavoriteResponse> {
        return userRepository.addFavorite(userId, speakerId)
    }

    fun deleteFavorite(userId: String, speakerId: String) {
        viewModelScope.launch {
            val result = userRepository.deleteFavorite(userId, speakerId)
            if (result is Results.Success) {
                _favorites.value = _favorites.value.filter { it.speakerId != speakerId }
            }
        }
    }

    fun getUserSession() = userRepository.getSession().asLiveData()
}