package com.victorhvs.rnm.data.datasources.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.victorhvs.rnm.core.DispatcherProvider
import com.victorhvs.rnm.data.datasources.remote.RNMService
import com.victorhvs.rnm.data.models.Character
import kotlinx.coroutines.withContext

class SearchCharacterPagingSource(
    private val rnmService: RNMService,
    private val query: String,
    private val dispatcher: DispatcherProvider
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return withContext(dispatcher.io()) {
            try {
                val apiResponse = rnmService.filterCharacters(query = query, page = params.key ?: 1)
                val characters = apiResponse.results
                if (characters.isNotEmpty()) {
                    LoadResult.Page(
                        data = characters,
                        prevKey = getPage(apiResponse.info.prev),
                        nextKey = getPage(apiResponse.info.next)
                    )
                } else {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    private fun getPage(url: String?): Int? =
        url?.substringAfter("?page=")?.substringBefore("&")?.toInt()

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? = state.anchorPosition
}
