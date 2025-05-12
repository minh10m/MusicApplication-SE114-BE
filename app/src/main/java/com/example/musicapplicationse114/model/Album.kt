package com.example.musicapplicationse114.model

import java.time.LocalDate

data class Album(val id : Long, val name : String,
                 val releseDate: String, val coverImage : String,
                 val description: String, val artist : Artist, val songs : ArrayList<Song>) {
}