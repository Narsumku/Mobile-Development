package com.bangkit.narsumku.data.request

import com.google.gson.annotations.SerializedName

data class AddFavoriteRequest(

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("speakerId")
    val speakerId: String
)