package com.example.musicapplicationse114.repositories

import com.example.musicapplicationse114.model.Album
import com.example.musicapplicationse114.model.RecentlyPlayed
import com.example.musicapplicationse114.model.Song
import kotlinx.coroutines.delay
import javax.inject.Inject

class ApiImpl @Inject constructor() : Api {
    private val songs = ArrayList<Song>()
    private val albums = ArrayList<Album>()
    private val recentPlayed = ArrayList<RecentlyPlayed>()

    override suspend fun login(username: String, password: String): Boolean {
        delay(2000)
        if (username != "TuanLee" || password != "1234") {
            throw Exception("Invalid username or password")
        }
        return true
    }

    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        delay(1000)
        if (username == "tuan@111" || username == "admin") {
            throw Exception("Username already exists")
        } else if (email == "tuanlee@gmail.com" || email == "admin@gmail.com") {
            throw Exception("Email already exists")
        } else if (password != confirmPassword) {
            throw Exception("Password not match")
        }
        return true
    }

    override suspend fun loadAlbums(): ArrayList<Album> {
        delay(1000)
        return albums
    }

    override suspend fun loadSong(): ArrayList<Song> {
        delay(1000)
        return songs
    }

    override suspend fun loadRecentPlayed(): ArrayList<RecentlyPlayed> {
        delay(1000)
        return recentPlayed
    }
}
