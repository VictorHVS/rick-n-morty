package com.victorhvs.rnm.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.victorhvs.rnm.R
import com.victorhvs.rnm.presentation.theme.RicknmortyTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RNMImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String
) {
    GlideImage(
        modifier = Modifier
            .semantics { testTagsAsResourceId = true }
            .testTag("RNMImage")
            .fillMaxWidth()
            .then(modifier),
        imageModel = { imageUrl },
        imageOptions = ImageOptions(
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center,
            contentDescription = contentDescription,
            colorFilter = null,
            alpha = 1f
        ),
        previewPlaceholder = R.drawable.ic_launcher_foreground,
        loading = {
            Box(modifier = Modifier.matchParentSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        failure = {
            Text(text = stringResource(id = R.string.image_load_error))
        }
    )
}

@Preview
@Composable
fun RNMImagePreview() {
    RicknmortyTheme {
        RNMImage(
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            contentDescription = "Rick Sanchez",
        )
    }
}
