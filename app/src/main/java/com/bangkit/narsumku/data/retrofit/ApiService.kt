package com.bangkit.narsumku.data.retrofit

import com.bangkit.narsumku.data.request.LoginRequest
import com.bangkit.narsumku.data.response.LoginResponse
import com.bangkit.narsumku.data.request.SignupRequest
import com.bangkit.narsumku.data.response.PopularSpeaker
import com.bangkit.narsumku.data.response.Speaker
import com.bangkit.narsumku.data.response.SignupResponse
import com.bangkit.narsumku.data.response.SpeakerDetailResponse
import com.bangkit.narsumku.data.response.SpeakerResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body signupRequest: SignupRequest
    ): SignupResponse

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users")
    suspend fun getAllSpeakers(): SpeakerResponse

    @GET("search")
    suspend fun getSearch(
        @Query("keyword") field: String
    ): List<Speaker>

    @GET("speaker/{id}")
    suspend fun getDetailSpeaker(
        @Path("id") id: String
    ): SpeakerDetailResponse

    @GET("popular")
    suspend fun getHomeForPopular(): List<PopularSpeaker>
}