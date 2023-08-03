package com.victorhvs.rnm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.victorhvs.rnm.presentation.screens.list.ListScreen
import com.victorhvs.rnm.presentation.theme.RicknmortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RicknmortyTheme {
                ListScreen()
            }
        }
    }
}
