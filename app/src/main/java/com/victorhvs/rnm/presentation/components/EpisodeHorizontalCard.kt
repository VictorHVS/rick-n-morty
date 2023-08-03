package com.victorhvs.rnm.presentation.components

import android.content.res.Configuration
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import com.victorhvs.rnm.presentation.theme.RicknmortyTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EpisodeHorizontalCard(
    modifier: Modifier = Modifier,
    position: Int,
    name: String,
    episode: String,
    airDate: String
) {
    Card(
        modifier = modifier
            .testTag("EpisodeHorizontalCard")
            .semantics { testTagsAsResourceId = true }
    ) {
        ListItem(
            colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            headlineContent = {
                Text(
                    text = name,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            overlineContent = {
                Text(
                    text = airDate,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            trailingContent = {
                Text(
                    text = episode,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            leadingContent = {
                CharRounded(text = position.toString())
            },
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EpisodeHorizontalCardPreview() {
    RicknmortyTheme {
        EpisodeHorizontalCard(
            name = "Pilot",
            episode = "S01E01",
            airDate = "December 2, 2013",
            position = 1
        )
    }
}
