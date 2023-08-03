package com.victorhvs.rnm.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    @SerialName("id") val id: Int,
    @SerialName("created") val created: String = "",
    @SerialName("air_date") val airDate: String = "--/--/----",
    @SerialName("characters") val characters: List<String> = emptyList(),
    @SerialName("episode") val episode: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("url") val url: String = ""
)
