package com.victorhvs.rnm.data.repositories

import androidx.paging.PagingData
import com.victorhvs.rnm.data.models.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun searchCharacter(query: String): Flow<PagingData<Character>>

    suspend fun getCharacter(id: Int): Character

}
