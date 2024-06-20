package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("dataUser")
    val dataUser: DataUser? = null
)

data class DataUser(

    @field:SerializedName("id")
    val userId: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String
)