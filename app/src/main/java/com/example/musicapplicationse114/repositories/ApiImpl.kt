package com.example.musicapplicationse114.repositories

import kotlinx.coroutines.delay
import javax.inject.Inject

class ApiImpl @Inject constructor() : Api {
    override suspend fun login(username: String, password: String): Boolean {
        delay(1000)
        if(username != "admin" || password != "1234")
        {
            throw Exception("Invalid username or password")
        }
        return true
    }

    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        delay(1000)
        if(username == "tuan@111" || username == "admin"){
            throw Exception("Username already exists")
        }
        else if(email == "tuanlee@gmail.com" || email == "admin@gmail.com")
            throw Exception("Email already exists")
        else if(password != confirmPassword)
            throw Exception("Password not match")
        return true
    }



}