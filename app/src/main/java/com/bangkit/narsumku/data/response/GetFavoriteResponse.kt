package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class GetFavoriteResponse(

    @field:SerializedName("speaker_id")
    val speakerId: String,

    @field:SerializedName("profile_pic_url")
    val profilePicUrl: String,

    @field:SerializedName("Name")
    val name: String,

    @field:SerializedName("Experience")
    val experience: String,

    @field:SerializedName("Rating")
    val rating: Double,

    @field:SerializedName("Field")
    val field: String
)