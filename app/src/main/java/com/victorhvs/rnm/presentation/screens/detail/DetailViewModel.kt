package com.victorhvs.rnm.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorhvs.rnm.core.DispatcherProvider
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.models.Episode
import com.victorhvs.rnm.data.repositories.CharacterRepository
import com.victorhvs.rnm.data.repositories.EpisodesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val charRepository: CharacterRepository,
    private val epiRepository: EpisodesRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> =
        _state.asStateFlow()

    fun fetchCharacterAndEpisodes(charId: Int) {
        viewModelScope.launch {
            try {
                val character = fetchCharacter(charId)
                val episodes = fetchEpisodes(character.episode)
                updateUiState(character, episodes)
            } catch (throwable: Throwable) {
                _state.update { UiState.Error(e = throwable) }
            }
        }
    }

    private suspend fun fetchCharacter(charId: Int) = withContext(dispatcherProvider.io()) {
        charRepository.getCharacter(id = charId)
    }

    private suspend fun fetchEpisodes(episodeUrls: List<String>) =
        withContext(dispatcherProvider.io()) {
            val episodeIds = episodeUrls.map { it.substringAfterLast("/").toInt() }
            epiRepository.getEpisodes(ids = episodeIds)
        }

    private fun updateUiState(character: Character, episodes: List<Episode>) {
        _state.update { uiState ->
            if (uiState is UiState.Success) {
                uiState.copy(
                    charInfo = character,
                    episodes = episodes
                )
            } else {
                UiState.Success(
                    charInfo = character,
                    episodes = episodes
                )
            }
        }
    }

    sealed class UiState {
        data class Success(
            val charInfo: Character,
            val episodes: List<Episode> = emptyList()
        ) : UiState()

        data object Loading : UiState()
        data class Error(val e: Throwable) : UiState()
    }
}
