package com.bangkit.narsumku.ui.search

import androidx.lifecycle.ViewModel
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.Speaker

class SearchViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun getSearch(field: String): Results<List<Speaker>> {
        return userRepository.getSearch(field)
    }
}