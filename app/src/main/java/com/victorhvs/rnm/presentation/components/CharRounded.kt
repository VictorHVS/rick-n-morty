package com.victorhvs.rnm.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victorhvs.rnm.presentation.theme.RicknmortyTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CharRounded(
    text: String,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .testTag("CharRounded")
            .semantics { testTagsAsResourceId = true }
            .size(40.dp)
            .background(
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharRoundedPreview() {
    RicknmortyTheme {
        CharRounded(text = "1")
    }
}
