package com.victorhvs.rnm.data.datasources.remote

import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.models.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RNMService {

    @GET("character")
    suspend fun filterCharacters(
        @Query("name") query: String,
        @Query("page") page: Int = 1
    ): ApiResponse<Character>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Character

    @GET("episode/{ids}")
    suspend fun getEpisodes(
        @Path("ids") ids: List<Int>
    ): List<Episode>

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        const val TIMEOUT_IN_SECONDS = 15L
    }
}
