package com.victorhvs.rnm.presentation.screens.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery get() = _searchQuery

    private val _searchedCharacter = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val searchedCharacter get() = _searchedCharacter.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchCharacter(query: String) {
        viewModelScope.launch {
            repository.searchCharacter(query = query).cachedIn(viewModelScope).collect {
                _searchedCharacter.value = it
            }
        }
    }
}
