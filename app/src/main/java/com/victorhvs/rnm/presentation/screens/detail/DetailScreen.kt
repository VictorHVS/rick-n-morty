package com.victorhvs.rnm.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.FilterFrames
import androidx.compose.material.icons.outlined.TheaterComedy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.victorhvs.rnm.R
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.models.Episode
import com.victorhvs.rnm.presentation.components.EpisodeHorizontalCard
import com.victorhvs.rnm.presentation.components.ProgressBar
import com.victorhvs.rnm.presentation.components.RNMImage
import com.victorhvs.rnm.presentation.components.SectionTitle
import com.victorhvs.rnm.presentation.theme.spacing

@Composable
fun DetailScreen(
    onBackPressed: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState()

    Scaffold { paddingValues ->
        when (val state = uiState.value) {
            is DetailViewModel.UiState.Error -> state.e.printStackTrace()
            DetailViewModel.UiState.Loading -> ProgressBar()
            is DetailViewModel.UiState.Success -> DetailContent(
                modifier = Modifier.padding(paddingValues),
                character = state.charInfo,
                episodes = state.episodes,
                onBackPressed = onBackPressed
            )
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    character: Character,
    episodes: List<Episode>,
    onBackPressed: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
    ) {
        item {
            CharInfoHeader(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                character = character,
                onBackPressed = onBackPressed
            )
        }

        if (episodes.isNotEmpty()) {
            item {
                SectionTitle(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .fillMaxWidth(),
                    title = R.string.episodes,
                    action = { }
                )
            }

            items(episodes) { episode ->
                EpisodeHorizontalCard(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    position = episode.id,
                    name = episode.name,
                    episode = episode.episode,
                    airDate = episode.airDate
                )
            }
        }
    }
}

@Composable
fun CharInfoHeader(
    modifier: Modifier = Modifier,
    character: Character,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            RNMImage(
                imageUrl = character.image,
                contentDescription = character.name
            )
            IconButton(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    ),
                onClick = {
                    onBackPressed.invoke()
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back button",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp)
                )
            }
        }
        Text(
            text = character.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        ) {
            SuggestionChip(
                onClick = { },
                label = { Text(text = character.status) },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Badge,
                        contentDescription = character.status,
                    )
                }
            )
            SuggestionChip(
                onClick = { },
                label = { Text(text = character.species) },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.FilterFrames,
                        contentDescription = character.status,
                    )
                }
            )
            SuggestionChip(
                onClick = { },
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.n_episodes,
                            character.episode.size
                        )
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.TheaterComedy,
                        contentDescription = character.status,
                    )
                }
            )
        }
    }
}
