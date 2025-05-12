package com.example.musicapplicationse114.ui.screen.likedsongs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicapplicationse114.MainViewModel
import com.example.musicapplicationse114.model.Album
import com.example.musicapplicationse114.model.Artist
import com.example.musicapplicationse114.model.Genre
import com.example.musicapplicationse114.model.Song
import com.example.musicapplicationse114.ui.screen.home.NavigationBar
import com.example.musicapplicationse114.ui.theme.MusicApplicationSE114Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.delay

@Composable
fun LikedSongsScreen(
    navController: NavController,
    viewModel: LikedSongsViewModel = viewModel(),
    mainViewModel: MainViewModel
) {
    val songs by viewModel.likedSongs.collectAsState()
    var showLoading by remember { mutableStateOf(false) }

    if (showLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(showLoading) {
        if (showLoading) {
            delay(1000)
            showLoading = false
        }
    }

    Scaffold(bottomBar = { NavigationBar(navController = navController) { showLoading = true } }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 80.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Liked Songs", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${songs.size} liked songs", fontSize = 14.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            SearchBar()
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(songs) { song ->
                    SongItem(song)
                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFF2C2C2C), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Search", color = Color.White.copy(alpha = 0.6f))
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.SwapVert, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
    }
}

@Composable
fun SongItem(song: Song) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .background(Color.Gray, RoundedCornerShape(5.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(song.title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(song.artist.name, color = Color.Gray, fontSize = 13.sp)
        }
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.LightGray)
        Spacer(modifier = Modifier.width(10.dp))
        Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.LightGray)
    }
}

class FakeLikedSongsViewModel(songs: List<Song>) : LikedSongsViewModel() {
    private val _songs = MutableStateFlow(songs)
    override val likedSongs: StateFlow<List<Song>> = _songs
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLikedSongsScreen() {
    MusicApplicationSE114Theme(darkTheme = true) {
        val dummyArtist = Artist(1, "The Chainsmokers", "", "", 0, arrayListOf(), arrayListOf(), arrayListOf())
        val dummyAlbum = Album(1, "Test Album", "2020-01-01", "", "", dummyArtist, arrayListOf())
        val dummyGenre = Genre(1, "EDM", "", arrayListOf())

        val songs = listOf(
            Song(1, "Inside Out", 220, "", "", "", "2020-01-01", 0, dummyAlbum, dummyArtist, dummyGenre),
            Song(2, "Young", 210, "", "", "", "2020-01-01", 0, dummyAlbum, dummyArtist, dummyGenre),
            Song(3, "Beach House", 250, "", "", "", "2020-01-01", 0, dummyAlbum, dummyArtist, dummyGenre),
            Song(4, "Kills You Slowly", 200, "", "", "", "2020-01-01", 0, dummyAlbum, dummyArtist, dummyGenre)
        )

        val fakeViewModel = FakeLikedSongsViewModel(songs)

        LikedSongsScreen(
            navController = rememberNavController(),
            viewModel = fakeViewModel,
            mainViewModel = MainViewModel()
        )
    }
}