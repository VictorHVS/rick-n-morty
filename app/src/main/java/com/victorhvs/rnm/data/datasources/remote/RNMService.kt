package com.victorhvs.rnm.data.datasources.remote

import com.victorhvs.rnm.data.models.Character
import retrofit2.http.GET
import retrofit2.http.Query

interface RNMService {

    @GET("character")
    suspend fun filterCharacters(
        @Query("name") query: String,
        @Query("page") page: Int = 1
    ): ApiResponse<Character>

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        const val TIMEOUT_IN_SECONDS = 15L
    }
}
