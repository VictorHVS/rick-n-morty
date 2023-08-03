package com.victorhvs.rnm.presentation.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.FilterFrames
import androidx.compose.material.icons.outlined.TheaterComedy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState()

    when (val state = uiState.value) {
        is DetailViewModel.UiState.Error -> state.e.printStackTrace()
        DetailViewModel.UiState.Loading -> ProgressBar()
        is DetailViewModel.UiState.Success -> DetailContent(
            character = state.charInfo,
            episodes = state.episodes
        )
    }
}

@Composable
fun DetailContent(
    character: Character,
    episodes: List<Episode>
) {
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
    ) {
        item {
            CharInfoHeader(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                character = character
            )
        }

        if (episodes.isNotEmpty()) {
            item {
                SectionTitle(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .fillMaxWidth(),
                    title = R.string.episodes
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
    character: Character
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RNMImage(
            imageUrl = character.image,
            contentDescription = character.name
        )
        Text(
            text = character.name,
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
