package com.victorhvs.rnm.data.repositories

import com.victorhvs.rnm.core.DispatcherProvider
import com.victorhvs.rnm.data.datasources.remote.RNMService
import com.victorhvs.rnm.data.models.Episode
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val rnmService: RNMService,
    private val dispatcher: DispatcherProvider
) : EpisodesRepository {
    override suspend fun getEpisodes(ids: List<Int>): List<Episode> {
        return withContext(dispatcher.io()) {
            rnmService.getEpisodes(ids = ids)
        }
    }
}
