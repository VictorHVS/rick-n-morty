package com.victorhvs.rnm.presentation.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.victorhvs.rnm.R
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.presentation.components.CharVerticalCard
import com.victorhvs.rnm.presentation.components.SearchWidget
import com.victorhvs.rnm.presentation.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    onCharacterClicked: (Int) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {

    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle()
    val pagingCharacters = viewModel.searchedCharacter.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.searchCharacter(viewModel.searchQuery.value)
    }

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = MaterialTheme.spacing.medium),
        ) {
            SearchWidget(
                modifier = Modifier,
                text = searchQuery.value,
                onTextChange = { viewModel.updateSearchQuery(it) },
                onSearchClicked = {
                    viewModel.searchCharacter(it)
                    coroutineScope.launch {
                        gridState.animateScrollToItem(0)
                    }
                },
            )

            ListContent(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                gridState = gridState,
                characters = pagingCharacters,
                onCardClicked = onCharacterClicked
            )
        }
    }
}

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    characters: LazyPagingItems<Character>,
    gridState: LazyGridState,
    onCardClicked: (Int) -> Unit
) {

    LazyVerticalGrid(
        state = gridState,
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        items(characters.itemCount) { index ->
            val character = characters[index]
            character?.let {
                CharVerticalCard(
                    onClick = { onCardClicked(character.id) },
                    imageUrl = character.image,
                    name = character.name,
                    species = character.species
                )
            }
        }
    }

    characters.apply {
        when {
            loadState.refresh is LoadState.Error -> println(loadState)
            loadState.append is LoadState.Error -> println(loadState.append.toString())
        }
    }

    if (characters.loadState.refresh !is LoadState.Loading && characters.itemCount == 0) {
        ListEmptyContent()
    }
}

@Composable
private fun ListEmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Empty Status"
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .fillMaxWidth(),
                text = stringResource(R.string.empty_characters),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
