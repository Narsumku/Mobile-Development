package com.bangkit.narsumku.data.request

import com.google.gson.annotations.SerializedName

data class PreferencesRequest (

    @field:SerializedName("fields")
    val listField: List<String>
)