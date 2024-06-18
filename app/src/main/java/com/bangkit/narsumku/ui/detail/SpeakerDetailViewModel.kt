package com.bangkit.narsumku.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.response.SpeakerDetailResponse
import kotlinx.coroutines.launch

class SpeakerDetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _speakerDetail = MutableLiveData<Results<SpeakerDetailResponse>>()
    val speakerDetail: LiveData<Results<SpeakerDetailResponse>> = _speakerDetail

    fun getSpeakerDetail(id: String) {
        viewModelScope.launch {
            _speakerDetail.value = userRepository.getDetailSpeaker(id)
        }
    }
}