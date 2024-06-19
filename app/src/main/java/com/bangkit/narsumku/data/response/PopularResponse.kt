package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class PopularSpeaker(

    @field:SerializedName("speaker_id")
    val speakerId: String,

    @field:SerializedName("profile_pic_url")
    val profilePicUrl: String,

    @field:SerializedName("Full Name")
    val fullName: String,

    @field:SerializedName("Rating")
    val rating: Double,

    @field:SerializedName("Experience")
    val experience: String,

    @field:SerializedName("Field")
    val field: String,

    @field:SerializedName("Favorite Count")
    val favoriteCount: Int? = null
)