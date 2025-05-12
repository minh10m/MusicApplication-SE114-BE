package com.example.musicapplicationse114.ui.screen.home

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicapplicationse114.Screen
import com.example.musicapplicationse114.common.enum.LoadStatus
import com.example.musicapplicationse114.common.enum.TimeOfDay
import com.example.musicapplicationse114.model.Album
import com.example.musicapplicationse114.model.RecentlyPlayed
import com.example.musicapplicationse114.model.Song
import com.example.musicapplicationse114.repositories.Api
import com.example.musicapplicationse114.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import org.threeten.bp.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

data class HomeUiState(
    val albums: List<Album> = emptyList(),
    val songs: List<Song> = emptyList(),
    val recentPlayed: List<RecentlyPlayed> = emptyList(),
    val status : LoadStatus = LoadStatus.Init(),
    val avatar : String = "",
    val username : String = "",
    val timeOfDay: TimeOfDay = TimeOfDay.MORNING
    )

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: Api?,
    private val mainLog: MainLog?
): ViewModel() {
    val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

//    fun loadSong()
//    {
//        viewModelScope.launch {
//            try {
//                if(api != null){
//                    val songs = api.loadSong()
//                    _uiState.value = _uiState.value.copy(songs = songs, status = LoadStatus.Success())
//                }
//            }catch(ex : Exception)
//            {
//                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
//            }
//        }
//    }
//
//    fun loadAlbum()
//    {
//        viewModelScope.launch {
//            try {
//                if (api != null) {
//                    val albums = api.loadAlbums()
//                    _uiState.value =
//                        _uiState.value.copy(albums = albums, status = LoadStatus.Success())
//                }
//            } catch (ex: Exception) {
//                _uiState.value =
//                    _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
//            }
//        }
//    }
//
//    fun loadRecentPlayed()
//    {
//        viewModelScope.launch {
//            try {
//                if(api != null){
//                    val recentPlayed = api.loadRecentPlayed()
//                    _uiState.value = _uiState.value.copy(recentPlayed = recentPlayed, status = LoadStatus.Success())
//                }
//            }catch (ex: Exception)
//            {
//                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
//            }
//        }
//    }

    fun setTimeOfDay(){
        val hour = LocalDateTime.now().hour
        val timeOfDay = when(hour){
            in 5..11 -> TimeOfDay.MORNING
            in 12..17 -> TimeOfDay.AFTERNOON
            else -> TimeOfDay.EVENING
        }
        _uiState.value = _uiState.value.copy(timeOfDay = timeOfDay)
    }

    fun updateUserName(username: String){
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun getUserName(): String{
        return _uiState.value.username
    }

    fun getTimeOfDay() : TimeOfDay{
        return _uiState.value.timeOfDay
    }
}