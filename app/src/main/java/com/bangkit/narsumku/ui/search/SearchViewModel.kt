package com.bangkit.narsumku.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.SearchResponse
import com.bangkit.narsumku.data.response.Speaker
import kotlinx.coroutines.Dispatchers

class SearchViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun getSearch(field: String): Results<List<Speaker>> {
        return userRepository.getSearch(field)
    }
}