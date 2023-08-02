package com.victorhvs.rnm.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.victorhvs.rnm.core.DispatcherProvider
import com.victorhvs.rnm.data.datasources.pagingsource.SearchCharacterPagingSource
import com.victorhvs.rnm.data.datasources.remote.RNMService
import com.victorhvs.rnm.data.models.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val rnmService: RNMService,
    private val dispatcher: DispatcherProvider
) : CharacterRepository {

    override fun searchCharacter(query: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = CHARS_PER_PAGE),
            pagingSourceFactory = {
                SearchCharacterPagingSource(
                    rnmService = rnmService,
                    query = query,
                    dispatcher = dispatcher,
                )
            },
        ).flow
    }

    companion object {
        const val CHARS_PER_PAGE = 20
    }
}
