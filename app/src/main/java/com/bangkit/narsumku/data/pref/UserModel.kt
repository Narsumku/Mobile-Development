package com.bangkit.narsumku.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)