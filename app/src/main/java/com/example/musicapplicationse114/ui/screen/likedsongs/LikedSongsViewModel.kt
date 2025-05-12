package com.example.musicapplicationse114.ui.screen.likedsongs

import androidx.lifecycle.ViewModel
import com.example.musicapplicationse114.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


open class LikedSongsViewModel : ViewModel() {

    // Khởi tạo rỗng tạm thời để tránh vòng lặp
    private val emptySongs = arrayListOf<Song>()
    private val emptyAlbums = arrayListOf<Album>()
    private val emptyFollows = arrayListOf<FollowArtist>()

    // Tạo artist trước (với songs và albums tạm thời rỗng)
    private val dummyArtist = Artist(
        id = 1,
        name = "The Chainsmokers",
        avatar = "",
        description = "Popular EDM duo",
        followersCount = 1234567,
        albums = emptyAlbums,
        songs = emptySongs,
        followers = emptyFollows
    )

    // Tạo genre rỗng (bắt buộc)
    private val dummyGenre = Genre(
        id = 1,
        name = "EDM",
        description = "Electronic Dance Music",
        Song = emptySongs
    )

    // Khởi tạo album với songs tạm thời rỗng
    private val dummyAlbum = Album(
        id = 1,
        name = "Memories...Do Not Open",
        releseDate = "2020-01-01",
        coverImage = "",
        description = "Debut album",
        artist = dummyArtist,
        songs = emptySongs
    )

    // Tạo danh sách bài hát thật
    private val dummySongs = arrayListOf(
        Song(1, "Inside Out", 220, "", "", "", "2020-01-01", 1000, dummyAlbum, dummyArtist, dummyGenre),
        Song(2, "Young", 210, "", "", "", "2020-01-01", 850, dummyAlbum, dummyArtist, dummyGenre),
        Song(3, "Beach House", 250, "", "", "", "2020-01-01", 920, dummyAlbum, dummyArtist, dummyGenre),
        Song(4, "Kills You Slowly", 200, "", "", "", "2020-01-01", 770, dummyAlbum, dummyArtist, dummyGenre)
    )

    // Cập nhật lại quan hệ
    private val updatedArtist = dummyArtist.copy(
        songs = dummySongs,
        albums = arrayListOf(dummyAlbum)
    )
    private val updatedAlbum = dummyAlbum.copy(
        songs = dummySongs,
        artist = updatedArtist
    )
    private val updatedGenre = dummyGenre.copy(
        Song = dummySongs
    )

    // Cuối cùng tạo danh sách bài hát final
    private val _likedSongs = MutableStateFlow(
        dummySongs.map {
            it.copy(
                album = updatedAlbum,
                artist = updatedArtist,
                genre = updatedGenre
            )
        }
    )

}
