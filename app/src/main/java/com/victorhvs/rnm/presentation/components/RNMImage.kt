package com.victorhvs.rnm.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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
        modifier = modifier
            .semantics { testTagsAsResourceId = true }
            .testTag("RNMImage")
            .aspectRatio(ratio = 1f),
        imageModel = { imageUrl },
        imageOptions = ImageOptions(
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center,
            contentDescription = contentDescription,
            colorFilter = null,
            alpha = 1f
        ),
        previewPlaceholder = R.drawable.app_icon,
        loading = {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1f),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = null
            )
        },
        failure = {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1f),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = null
            )
        },
        requestOptions = {
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
