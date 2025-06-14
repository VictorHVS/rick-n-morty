package com.victorhvs.rnm.presentation.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import com.victorhvs.rnm.R
import com.victorhvs.rnm.presentation.theme.RicknmortyTheme

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    action: (() -> Unit)? = { },
) {
    Row(
        modifier = modifier
            .semantics { testTagsAsResourceId = true }
            .testTag("SectionTitle")
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
//        action?.let {
//            IconButton(onClick = { action.invoke() }) {
//                Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null)
//            }
//        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SectionTitlePreview() {
    RicknmortyTheme {
        SectionTitle(
            title = R.string.episodes,
            action = {

            }
        )
    }
}
