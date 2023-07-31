package com.victorhvs.rnm.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victorhvs.rnm.R
import com.victorhvs.rnm.presentation.theme.RicknmortyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharVerticalCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    species: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .testTag("CharVerticalCard")
            .widthIn(min = 120.dp, max = 240.dp),
        colors = CardDefaults.outlinedCardColors(),
        shape = CardDefaults.outlinedShape,
        border = CardDefaults.outlinedCardBorder(),
        onClick = onClick
    ) {
        RNMImage(
            imageUrl = imageUrl,
            contentDescription = stringResource(id = R.string.character_image, name)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = species,
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharVerticalCardPreview() {
    RicknmortyTheme {
        CharVerticalCard(
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            name = "Rick Sanchez",
            species = "Human"
        )
    }
}
