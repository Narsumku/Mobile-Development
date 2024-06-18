package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class SpeakerResponse (

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listUser")
    val listUser: List<User>? = null
)

data class User (

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String
)