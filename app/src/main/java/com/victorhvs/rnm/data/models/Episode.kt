package com.victorhvs.rnm.data.models

import kotlinx.serialization.SerialName

data class Episode(
    @SerialName("id") val id: String,
    @SerialName("created") val created: String = "",
    @SerialName("air_date") val airDate: String = "--/--/----",
    @SerialName("characters") val characters: List<String> = emptyList(),
    @SerialName("episode") val episode: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("url") val url: String = ""
)
