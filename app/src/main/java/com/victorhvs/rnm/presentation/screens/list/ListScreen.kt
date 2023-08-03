package com.victorhvs.rnm.presentation.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.presentation.components.CharVerticalCard
import com.victorhvs.rnm.presentation.components.ProgressBar
import com.victorhvs.rnm.presentation.components.SearchWidget
import com.victorhvs.rnm.presentation.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
) {

    val pagingCharacters = viewModel.searchedCharacter.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.searchCharacter("")
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchWidget(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium),
                text = viewModel.searchQuery.value,
                onTextChange = { viewModel.updateSearchQuery(it) },
                onSearchClicked = {
                    viewModel.searchCharacter(it)
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues),
        ) {
            ListContent(
                characters = pagingCharacters,
            )
        }
    }
}

@Composable
fun ListContent(
    characters: LazyPagingItems<Character>,
) {

    LazyVerticalGrid(
        modifier = Modifier.padding(MaterialTheme.spacing.medium),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        items(characters.itemCount) { index ->
            val character = characters[index]
            character?.let {
                CharVerticalCard(
                    imageUrl = character.image,
                    name = character.name,
                    species = character.species
                )
            }
        }
    }

    characters.apply {
        when {
            loadState.refresh is LoadState.Loading -> ProgressBar()
            loadState.refresh is LoadState.Error -> println(loadState)
            loadState.append is LoadState.Loading -> ProgressBar()
            loadState.append is LoadState.Error -> println(loadState.append.toString())
        }
    }
}