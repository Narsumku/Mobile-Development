package com.bangkit.narsumku.data.request

import com.google.gson.annotations.SerializedName

data class SignupRequest(

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)
