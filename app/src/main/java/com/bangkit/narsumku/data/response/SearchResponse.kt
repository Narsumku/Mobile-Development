package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse (

    @field:SerializedName("speakers")
    val speakers: List<Speaker>
)

data class Speaker(

    @field:SerializedName("speaker_id")
    val speakerId: String,

    @field:SerializedName("profile_pic_url")
    val profilePicUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("field")
    val field: String
)