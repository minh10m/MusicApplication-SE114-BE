package com.example.musicapplicationse114.ui.screen.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.musicapplicationse114.repositories.Api
import com.example.musicapplicationse114.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: Api?,
    private val mainLog: MainLog?
): ViewModel() {
}