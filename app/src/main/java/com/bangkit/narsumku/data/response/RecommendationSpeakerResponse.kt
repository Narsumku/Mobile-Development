package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class RecommendationSpeakerResponse(

    @field:SerializedName("speaker_id")
    val speakerId: String,

    @field:SerializedName("Full Name")
    val fullName: String,

    @field:SerializedName("Rating")
    val rating: Double,

    @field:SerializedName("Experience")
    val experience: String,

    @field:SerializedName("Profile Picture")
    val profilePicture: String,

    @field:SerializedName("Field")
    val field: String
)