package com.example.musicapplicationse114.repositories

interface Api {
    suspend fun login(username: String, password:String):Boolean
    suspend fun signUp(username: String, email: String, password: String, confirmPassword: String):Boolean
}