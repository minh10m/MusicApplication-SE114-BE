package com.example.musicapplicationse114.model

import java.time.LocalDate

data class Artist(val id: Long, val name: String,
                  val avatar: String, val description: String,
                  val followersCount: Int = 0, val albums: ArrayList<Album>,
                  val songs : ArrayList<Song>, val followers: ArrayList<FollowArtist>){
}
