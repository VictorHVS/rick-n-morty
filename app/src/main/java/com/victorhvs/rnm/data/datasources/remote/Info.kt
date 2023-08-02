package com.victorhvs.rnm.data.datasources.remote

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)
