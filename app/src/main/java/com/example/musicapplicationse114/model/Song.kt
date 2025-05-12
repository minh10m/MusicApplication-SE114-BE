package com.example.musicapplicationse114.model

data class Song(
    val id: Long,
    val title: String,
    val duration: Int,
    val audioUrl: String,
    val thumbnail: String,
    val lyrics: String,
    val releaseDate: String, // <-- Đổi từ LocalDate
    val viewCount: Int = 0,
    val album: Album,
    val artist: Artist,
    val genre: Genre
)
