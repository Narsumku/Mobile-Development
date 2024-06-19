package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)