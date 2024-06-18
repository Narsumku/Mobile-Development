package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class SpeakerDetailResponse(

    @field:SerializedName("speaker_id")
    val speakerId: String,

    @field:SerializedName("profile_pic_url")
    val profilePicUrl: String,

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("occupation")
    val occupation: String,

    @field:SerializedName("headline")
    val headline: String,

    @field:SerializedName("summary")
    val summary: String,

    @field:SerializedName("recent_experience")
    val recentExperience: Int,

    @field:SerializedName("experience")
    val experience: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("category1")
    val category1: String,

    @field:SerializedName("category2")
    val category2: String,

    @field:SerializedName("category3")
    val category3: String
)