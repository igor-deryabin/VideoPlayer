package com.example.player.domain.data

import java.io.Serializable

data class Channel(
    val id: Int,
    val name: String,
    val descShort: String,
    val descFull: String,
    val image: String,
    val url: String,
    var isFavorite: Boolean = false
) : Serializable