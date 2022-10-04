package com.example.player.domain.data

data class Quality(
    val isAuto: Boolean = false,
    val bitrate: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val index:Int = 0,
    var selected: Boolean = false
)
