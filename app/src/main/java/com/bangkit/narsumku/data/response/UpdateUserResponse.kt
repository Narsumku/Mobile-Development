package com.bangkit.narsumku.data.response

import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(

    @field:SerializedName("error")
    val error: String,

    @field:SerializedName("message")
    val message: String
)
