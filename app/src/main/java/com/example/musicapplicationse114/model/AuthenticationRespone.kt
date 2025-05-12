package com.example.musicapplicationse114.model

import android.provider.ContactsContract.CommonDataKinds.Phone
import com.example.musicapplicationse114.common.enum.Role

data class AuthenticationRespone(
val accessToken : String?,
val refreshToken : String?,
val message: String,
)