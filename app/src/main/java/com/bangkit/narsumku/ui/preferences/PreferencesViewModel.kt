package com.bangkit.narsumku.ui.preferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.pref.UserModel
import com.bangkit.narsumku.data.request.PreferencesRequest
import com.bangkit.narsumku.data.response.PreferencesResponse
import kotlinx.coroutines.flow.map

class PreferencesViewModel(private val userRepository: UserRepository) : ViewModel()  {

    suspend fun setPreferences(
        userId: String,
        business: String,
        entertainment: String,
        politics: String,
        sport: String,
        tech: String,
        healthcare: String,
        academic: String,
        media: String
    ): Results<PreferencesResponse> {
        val client = PreferencesRequest(
            listField = listOf(business, entertainment, politics, sport, tech, healthcare, academic, media).filter { it.isNotEmpty() }
        )

        val result = userRepository.setPreferences(userId, client)

        if (result is Results.Success) {
            updateSessionAfterPreferences()
        }

        return result
    }

    private suspend fun updateSessionAfterPreferences() {
        userRepository.getSession().collect { userModel ->
            val updatedUserModel = userModel.copy(fillPreferences = true)
            userRepository.saveSession(updatedUserModel)
        }
    }

    fun getUserSession(): LiveData<String> {
        return userRepository.getSession().map { it.userId }.asLiveData()
    }

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}