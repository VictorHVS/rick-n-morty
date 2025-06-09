package com.victorhvs.rnm.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEBOUNCE_TIMEOUT = 400L

@OptIn(FlowPreview::class)
@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchedCharacter = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val searchedCharacter: StateFlow<PagingData<Character>> =
        _searchedCharacter.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(DEBOUNCE_TIMEOUT)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotBlank()) {
                        searchCharacter(query)
                    } else {
                        _searchedCharacter.value = PagingData.empty()
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchCharacter(query: String) {
        viewModelScope.launch {
            repository.searchCharacter(query = query).cachedIn(viewModelScope).collect { result ->
                _searchedCharacter.update {
                    result
                }
            }
        }
    }
}
