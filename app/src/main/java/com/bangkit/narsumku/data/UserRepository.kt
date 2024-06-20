package com.bangkit.narsumku.data

import android.util.Log
import com.bangkit.narsumku.data.pref.UserModel
import com.bangkit.narsumku.data.pref.UserPreference
import com.bangkit.narsumku.data.request.AddFavoriteRequest
import com.bangkit.narsumku.data.request.DeleteFavoriteRequest
import com.bangkit.narsumku.data.request.LoginRequest
import com.bangkit.narsumku.data.request.PreferencesRequest
import com.bangkit.narsumku.data.request.SignupRequest
import com.bangkit.narsumku.data.request.UpdateUserRequest
import com.bangkit.narsumku.data.response.AddFavoriteResponse
import com.bangkit.narsumku.data.response.DeleteFavoriteResponse
import com.bangkit.narsumku.data.response.ErrorResponse
import com.bangkit.narsumku.data.response.GetFavoriteResponse
import com.bangkit.narsumku.data.response.LoginResponse
import com.bangkit.narsumku.data.response.PopularSpeaker
import com.bangkit.narsumku.data.response.PreferencesResponse
import com.bangkit.narsumku.data.response.RecommendationSpeakerResponse
import com.bangkit.narsumku.data.response.SignupResponse
import com.bangkit.narsumku.data.response.Speaker
import com.bangkit.narsumku.data.response.SpeakerDetailResponse
import com.bangkit.narsumku.data.response.UpdateUserResponse
import com.bangkit.narsumku.data.retrofit.ApiConfig
import com.bangkit.narsumku.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun signup(name: String, email: String, password: String): Results<SignupResponse> {
        Results.Loading
        return try {
            val response = SignupRequest(name, email, password)
            val client = apiService.register(response)
            if (client.error == true) {
                Results.Error(client.message ?: "Unknown error")
            } else {
                Results.Success(client)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Results.Error(errorMessage.toString())
        }
    }

    suspend fun login(email: String, password: String): Results<LoginResponse> {
        Results.Loading
        return try {
            val response = LoginRequest(email, password)
            val client = apiService.login(response)

            if (client.error) {
                Results.Error(client.message)
            } else {
                val session = client.loginResult?.let {
                    UserModel(
                        email = email,
                        username = it.username,
                        userId = it.userId,
                        token = it.token,
                        isLogin = true,
                        fillPreferences = it.fillPreferences
                    )
                }
                if (session != null) {
                    saveSession(session)
                }
                client.loginResult?.let { ApiConfig.updateToken(it.token) }
                Results.Success(client)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Results.Error(errorMessage.toString())
        }
    }

    suspend fun getSearch(field: String): Results<List<Speaker>> {
        return try {
            val response = apiService.getSearch(field)
            Results.Success(response)
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun getDetailSpeaker(id: String): Results<SpeakerDetailResponse> {
        return try {
            val response = apiService.getDetailSpeaker(id)
            Results.Success(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Results.Error(errorMessage.toString())
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun getHomeForPopular(): Results<List<PopularSpeaker>> {
        return try {
            val response = apiService.getHomeForPopular()
            Log.d("UserRepository", "Home Response: ${Gson().toJson(response)}")
            Results.Success(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Log.e("UserRepository", "HTTP Exception: $errorMessage")
            Results.Error(errorMessage.toString())
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception: ${e.localizedMessage}")
            Results.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun getHomeForRecommendation(userId: String): Results<List<RecommendationSpeakerResponse>> {
        return try {
            val response = apiService.getHomeForRecommendation(userId)
            Log.d("UserRepository", "Home Response: ${Gson().toJson(response)}")
            Results.Success(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Log.e("UserRepository", "HTTP Exception: $errorMessage")
            Results.Error(errorMessage.toString())
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception: ${e.localizedMessage}")
            Results.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun addFavorite(userId: String, speakerId: String): Results<AddFavoriteResponse> {
        return try {
            val client = AddFavoriteRequest(userId, speakerId)
            val response = apiService.addFavorite(client)
            Results.Success(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Results.Error(errorMessage.toString())
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun deleteFavorite(userId: String, speakerId: String): Results<DeleteFavoriteResponse> {
        return try {
            val client = DeleteFavoriteRequest(userId, speakerId)
            val response = apiService.deleteFavorite(client)
            Results.Success(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Results.Error(errorMessage.toString())
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun getFavorite(userId: String): Flow<List<GetFavoriteResponse>> {
        return try {
            val response = apiService.getFavorite(userId)
            Log.d("UserRepository", "Get Favorite Response: ${Gson().toJson(response)}")
            flowOf(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Log.e("UserRepository", "HTTP Exception: $errorMessage")
            flowOf(emptyList())
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception: ${e.localizedMessage}")
            flowOf(emptyList())
        }
    }

    suspend fun updateUser(userId: String, username: String, email: String, password: String): Results<UpdateUserResponse> {
        return try {
            val client = UpdateUserRequest(username, email, password)
            val response = apiService.updateUser(userId, client)
            Results.Success(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Results.Error(errorMessage.toString())
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun setPreferences(
        userId: String,
        preferencesRequest: PreferencesRequest
    ): Results<PreferencesResponse> {
        return try {
            val response = apiService.setPreferences(userId, preferencesRequest)
            if (response.message == "Preferences submitted successfully.") {
                val updatedUserModel = userPreference.getSession().first().copy(fillPreferences = true)
                saveSession(updatedUserModel)
            }
            Results.Success(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Results.Error(errorMessage.toString())
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}