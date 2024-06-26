package com.bangkit.narsumku.data.retrofit

import com.bangkit.narsumku.data.request.AddFavoriteRequest
import com.bangkit.narsumku.data.request.DeleteFavoriteRequest
import com.bangkit.narsumku.data.request.LoginRequest
import com.bangkit.narsumku.data.request.PreferencesRequest
import com.bangkit.narsumku.data.request.SignupRequest
import com.bangkit.narsumku.data.request.UpdateUserRequest
import com.bangkit.narsumku.data.response.AddFavoriteResponse
import com.bangkit.narsumku.data.response.DeleteFavoriteResponse
import com.bangkit.narsumku.data.response.DeleteUserResponse
import com.bangkit.narsumku.data.response.GetFavoriteResponse
import com.bangkit.narsumku.data.response.GetUserResponse
import com.bangkit.narsumku.data.response.LoginResponse
import com.bangkit.narsumku.data.response.PopularSpeaker
import com.bangkit.narsumku.data.response.PreferencesResponse
import com.bangkit.narsumku.data.response.RecommendationSpeakerResponse
import com.bangkit.narsumku.data.response.SignupResponse
import com.bangkit.narsumku.data.response.Speaker
import com.bangkit.narsumku.data.response.SpeakerDetailResponse
import com.bangkit.narsumku.data.response.UpdateUserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
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

    @GET("recommendations/{userId}")
    suspend fun getHomeForRecommendation(
        @Path("userId") userId: String
    ): List<RecommendationSpeakerResponse>

    @POST("favorites")
    suspend fun addFavorite(
        @Body addFavoriteRequest: AddFavoriteRequest
    ): AddFavoriteResponse

    @HTTP(method = "DELETE", path = "favorites", hasBody = true)
    suspend fun deleteFavorite(
        @Body deleteFavoriteRequest: DeleteFavoriteRequest
    ): DeleteFavoriteResponse

    @GET("favorites/{userId}")
    suspend fun getFavorite(
        @Path("userId") userId: String
    ): List<GetFavoriteResponse>

    @PATCH("users/update/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: String,
        @Body updateUserRequest: UpdateUserRequest
    ): UpdateUserResponse

    @POST("preference/{userId}")
    suspend fun setPreferences(
        @Path("userId") userId: String,
        @Body preferencesRequest: PreferencesRequest
    ): PreferencesResponse

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): GetUserResponse

    @DELETE("users/delete/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: String
    ): DeleteUserResponse
}