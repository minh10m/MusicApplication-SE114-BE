package com.example.musicapplicationse114.repositories

import com.example.musicapplicationse114.model.AuthenticationResponse
import com.example.musicapplicationse114.model.UserLoginRequest
import com.example.musicapplicationse114.model.UserSignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
//    suspend fun login1(username: String, password:String):Boolean
//    suspend fun signUp1(username: String, email: String, password: String, confirmPassword: String):Boolean
//    suspend fun loadAlbums():ArrayList<Album>
//    suspend fun loadSong():ArrayList<Song>
//    suspend fun loadRecentPlayed():ArrayList<RecentlyPlayed>

    @POST("/login")
    suspend fun login(@Body request : UserLoginRequest): Response<AuthenticationResponse>

    @POST("/register")
    suspend fun register(@Body request: UserSignUpRequest): Response<AuthenticationResponse>

}