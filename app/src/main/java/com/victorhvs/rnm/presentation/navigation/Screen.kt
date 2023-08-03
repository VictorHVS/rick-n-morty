package com.victorhvs.rnm.presentation.navigation

sealed class Screen(val route: String) {
    object List : Screen("list_screen")
    data object Details : Screen("charDetail/{charId}") {
        fun passCharId(charId: Int) = "charDetail/$charId"
    }

    companion object {
        const val DETAILS_ARGUMENT_KEY = "charId"
    }
}
