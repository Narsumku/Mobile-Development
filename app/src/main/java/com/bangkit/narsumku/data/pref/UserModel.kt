package com.bangkit.narsumku.data.pref

data class UserModel(
    val email: String,
    val username: String,
    val userId: String,
    val token: String,
    val isLogin: Boolean = false
)