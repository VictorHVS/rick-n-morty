package com.victorhvs.rnm.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.models.Episode
import com.victorhvs.rnm.data.repositories.CharacterRepository
import com.victorhvs.rnm.data.repositories.EpisodesRepository
import com.victorhvs.rnm.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val charRepository: CharacterRepository,
    private val epiRepository: EpisodesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> =
        _state.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching {
                val charId = savedStateHandle.get<Int>(Screen.DETAILS_ARGUMENT_KEY)
                val charResult = charRepository.getCharacter(id = charId!!)
                    .also { char ->
                        _state.update {
                            UiState.Success(
                                charInfo = char
                            )
                        }
                    }
                epiRepository.getEpisodes(ids = charResult.episode.map {
                    it.substringAfterLast("/").toInt()
                }).also { episodes ->
                    _state.update {
                        UiState.Success(
                            charInfo = charResult,
                            episodes = episodes
                        )
                    }
                }
            }.onFailure { throwable ->
                _state.update {
                    UiState.Error(
                        e = throwable
                    )
                }
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
