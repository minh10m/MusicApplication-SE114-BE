package com.example.musicapplicationse114.model

import androidx.compose.ui.semantics.Role
import java.time.LocalDateTime

data class User(val id: Long, val role: Role, val username: String, val email: String,
                val phone : String, val password : String, val avatar : String,
                val createdAt: LocalDateTime, val updatedAt: LocalDateTime,
                val tokens : ArrayList<Token>)       {
}