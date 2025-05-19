package com.example.musicapplicationse114.ui.screen.playlists

import androidx.lifecycle.ViewModel
import com.example.musicapplicationse114.R
import com.example.musicapplicationse114.model.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlaylistViewModel : ViewModel() {
    private val dummyPlaylists = listOf(
        Playlist("Maroon 5 Songs", "Playlist • Myself", R.drawable.maroon5),
        Playlist("Phonk Madness", "Playlist", R.drawable.phonk),
        Playlist("Gryffin Collections", "Playlist • Myself", R.drawable.gryffin),
        Playlist("John Wick Chapter 4", "Album", R.drawable.johnwick),
        Playlist("EDM Night", "Playlist", R.drawable.edm),
        Playlist("Party Mix", "Playlist", R.drawable.party)
    )

    private val _playlists = MutableStateFlow(dummyPlaylists)
    val playlists: StateFlow<List<Playlist>> = _playlists
}
