package com.bangkit.narsumku.di

import android.content.Context
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.pref.UserPreference
import com.bangkit.narsumku.data.pref.dataStore
import com.bangkit.narsumku.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        ApiConfig.updateToken(user.token)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}