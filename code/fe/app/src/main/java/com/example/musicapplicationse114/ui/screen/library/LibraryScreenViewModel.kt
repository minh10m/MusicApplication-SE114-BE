package com.example.musicapplicationse114.ui.screen.library

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.musicapplicationse114.R
import com.example.musicapplicationse114.repositories.Api
import com.example.musicapplicationse114.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class RecentLibraryItem(
    val title: String,
    val subtitle: String,
    val imageRes: Int
)

data class LibraryUiState(
    val likedCount: Int = 0,
    val downloadedCount: Int = 0,
    val playlistCount: Int = 0,
    val artistCount: Int = 0,
    val recentItems: List<RecentLibraryItem> = emptyList()
)

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val mainLog : MainLog?,
    private val api : Api?
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        LibraryUiState(
            likedCount = 120,
            downloadedCount = 210,
            playlistCount = 12,
            artistCount = 3,
            recentItems = listOf(
                RecentLibraryItem("Inside Out", "The Chainsmokers, Charlee", R.drawable.cover1),
                RecentLibraryItem("Young", "The Chainsmokers", R.drawable.cover2),
                RecentLibraryItem("Beach House", "The Chainsmokers - Sick", R.drawable.cover3),
                RecentLibraryItem("Kills You Slowly", "The Chainsmokers - World", R.drawable.cover4),
                RecentLibraryItem("Setting Fires", "The Chainsmokers, XYLO", R.drawable.cover5),
                RecentLibraryItem("Somebody", "The Chainsmokers, Drew", R.drawable.cover6)
            )
        )
    )
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()
}
