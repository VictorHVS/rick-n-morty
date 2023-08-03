package com.victorhvs.rnm.data.datasources.remote

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val info: Info,
    val results: List<T>
)
