package com.victorhvs.rnm.data.repositories

import com.victorhvs.rnm.data.models.Episode

interface EpisodesRepository {

    suspend fun getEpisodes(ids: List<Int>): List<Episode>
}
