package com.example.musicapplicationse114.repositories

import com.example.musicapplicationse114.model.Album
import com.example.musicapplicationse114.model.RecentlyPlayed
import com.example.musicapplicationse114.model.Song

interface Api {
    suspend fun login(username: String, password: String): Boolean

    suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean

    suspend fun loadAlbums(): ArrayList<Album>
    suspend fun loadSong(): ArrayList<Song>
    suspend fun loadRecentPlayed(): ArrayList<RecentlyPlayed>
}
