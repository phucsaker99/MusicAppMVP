package com.example.musicappmvp.data.model

data class Song(
    val id: Long,
    val size: Int,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Int,
    val data: String
)
