package com.example.musicapplicationse114.ui.screen.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicapplicationse114.MainViewModel
import com.example.musicapplicationse114.R
import com.example.musicapplicationse114.model.Album
import com.example.musicapplicationse114.model.Artist
import com.example.musicapplicationse114.model.Genre
import com.example.musicapplicationse114.model.Song
import com.example.musicapplicationse114.ui.theme.MusicApplicationSE114Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayerViewModel : ViewModel() {
    private val dummyArtist = Artist(1, "The Chainsmokers", "", "", 0, arrayListOf(), arrayListOf(), arrayListOf())
    private val dummyAlbum = Album(1, "Test Album", "2020-01-01", "", "", dummyArtist, arrayListOf())
    private val dummyGenre = Genre(1, "EDM", "", arrayListOf())

    private val dummySong = Song(
        id = 1,
        title = "Inside Out",
        duration = 195,
        audioUrl = "",
        thumbnail = "",
        lyrics = "",
        releaseDate = "2020-01-01",
        viewCount = 1000,
        album = dummyAlbum,
        artist = dummyArtist,
        genre = dummyGenre
    )

    private val _currentSong = MutableStateFlow(dummySong)
    val currentSong: StateFlow<Song> = _currentSong

    private val _queue = MutableStateFlow(List(5) { i ->
        dummySong.copy(id = i + 2L, title = "Song ${i + 2}")
    })
    val queue: StateFlow<List<Song>> = _queue
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    navController: NavController,
    viewModel: PlayerViewModel = viewModel(),
    mainViewModel: MainViewModel,
    onCollapse: () -> Unit = { navController.popBackStack() }
) {
    val song by viewModel.currentSong.collectAsState()
    val queue by viewModel.queue.collectAsState()
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.down),
                        contentDescription = "Collapse",
                        tint = Color.White,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { onCollapse() } // 👈 gọi lambda thay vì hardcode
                    )

                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(inner)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.cover1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song.title,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = song.artist.name,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.White)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Download, contentDescription = null, tint = Color.White)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
                }
            }

            Spacer(Modifier.height(16.dp))
            Column {
                Slider(
                    value = progress,
                    onValueChange = { progress = it },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("0:25", color = Color.Gray, fontSize = 12.sp)
                    Text("3:15", color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Shuffle, contentDescription = null, tint = Color.White)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.SkipPrevious, contentDescription = null, tint = Color.White)
                }
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { isPlaying = !isPlaying }) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = Color.Black,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.SkipNext, contentDescription = null, tint = Color.White)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Loop, contentDescription = null, tint = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Up Next", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(queue) { item ->
                    SmallQueueItem(item)
                }
            }
        }
    }
}

@Composable
fun SmallQueueItem(song: Song) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(song.title, color = Color.White, fontSize = 14.sp)
            Text(song.artist.name, color = Color.Gray, fontSize = 12.sp)
        }
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.padding(end = 8.dp)
        )
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null,
            tint = Color.LightGray
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPlayerScreen() {
    MusicApplicationSE114Theme(darkTheme = true) {
        PlayerScreen(
            navController = rememberNavController(),
            viewModel = PlayerViewModel(),
            mainViewModel = MainViewModel()
        )
    }
}