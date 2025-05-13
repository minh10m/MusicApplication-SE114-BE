package com.example.musicapplicationse114.ui.screen.likedsongs

import androidx.lifecycle.ViewModel
import com.example.musicapplicationse114.model.Album
import com.example.musicapplicationse114.model.Artist
import com.example.musicapplicationse114.model.Genre
import com.example.musicapplicationse114.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class LikedSongsViewModel : ViewModel() {
    private val dummyArtist = Artist(
        id = 1,
        name = "The Chainsmokers",
        avatar = "",
        description = "",
        followersCount = 0,
        albums = arrayListOf(),
        songs = arrayListOf(),
        followers = arrayListOf()
    )

    private val dummyAlbum = Album(
        id = 1,
        name = "Test Album",
        releseDate = "2020-01-01",
        coverImage = "",
        description = "",
        artist = dummyArtist,
        songs = arrayListOf()
    )

    private val dummyGenre = Genre(
        id = 1,
        name = "EDM",
        description = "",
        Song = arrayListOf()
    )

    private val dummySongs = listOf(
        Song(1, "Inside Out", 220, "", "", "", "2020-01-01", 0, dummyAlbum, dummyArtist, dummyGenre),
        Song(2, "Young", 210, "", "", "", "2020-01-01", 0, dummyAlbum, dummyArtist, dummyGenre),
        Song(3, "Beach House", 250, "", "", "", "2020-01-01", 0, dummyAlbum, dummyArtist, dummyGenre),
        Song(4, "Kills You Slowly", 200, "", "", "", "2020-01-01", 0, dummyAlbum, dummyArtist, dummyGenre)
    )

    private val _likedSongs = MutableStateFlow(dummySongs)
    open val likedSongs: StateFlow<List<Song>> = _likedSongs
}
