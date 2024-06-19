package com.bangkit.narsumku.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.di.Injection
import com.bangkit.narsumku.ui.detail.SpeakerDetailViewModel
import com.bangkit.narsumku.ui.favorite.FavoriteViewModel
import com.bangkit.narsumku.ui.fragment.HomeViewModel
import com.bangkit.narsumku.ui.login.LoginViewModel
import com.bangkit.narsumku.ui.main.MainViewModel
import com.bangkit.narsumku.ui.search.SearchViewModel
import com.bangkit.narsumku.ui.signup.SignupViewModel

class ViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SpeakerDetailViewModel::class.java) -> {
                SpeakerDetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}