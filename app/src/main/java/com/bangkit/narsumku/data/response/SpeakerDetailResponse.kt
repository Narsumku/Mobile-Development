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

    @field:SerializedName("1st Recent Experience")
    val recentExperience: Int,

    @field:SerializedName("Experience")
    val experience: String,

    @field:SerializedName("Email")
    val email: String,

    @field:SerializedName("Category_1")
    val category1: String,

    @field:SerializedName("Category_2")
    val category2: String,

    @field:SerializedName("Category_3")
    val category3: String
)