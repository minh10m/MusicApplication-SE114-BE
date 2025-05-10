package com.example.musicapplicationse114.ui.screen.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplicationse114.common.enum.LoadStatus
import com.example.musicapplicationse114.common.enum.Role
import com.example.musicapplicationse114.model.UserSignUpRequest
import com.example.musicapplicationse114.repositories.Api
import com.example.musicapplicationse114.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val successMessage: String = "",
    val confirmPassword: String = "",
    var isShowPassword: Boolean = false,
    var isShowConfirmPassword: Boolean = false,
    val status: LoadStatus = LoadStatus.Init()
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val mainLog: MainLog?,
    private val api: Api?
): ViewModel() {
    val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun updateEmail(email: String)
    {
        _uiState.value = _uiState.value.copy(email = email)

    }

    fun updatePassword(password: String)
    {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updateConfirmPassword(confirmPassword: String)
    {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun reset()
    {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Init())

    }

    fun changIsShowPassword()
    {
        _uiState.value = _uiState.value.copy(isShowPassword = !_uiState.value.isShowPassword)

    }

    fun changIsShowConfirmPassword(){
        _uiState.value = _uiState.value.copy(isShowConfirmPassword = !_uiState.value.isShowConfirmPassword)
    }

    fun updateSuccessMessage(successMessage: String)
    {
        _uiState.value = _uiState.value.copy(successMessage = successMessage)
    }

    fun signUp1()
    {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val result = api?.signUp1(_uiState.value.username, _uiState.value.email, _uiState.value.password, _uiState.value.confirmPassword)
                _uiState.value = _uiState.value.copy(status = LoadStatus.Success())
            }catch(ex: Exception)
            {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
            }
        }
    }

    fun signUp(){
        _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
        viewModelScope.launch {
            try {
                val result = api?.signUp(UserSignUpRequest(_uiState.value.username, _uiState.value.password, _uiState.value.email, "", "", Role.USER))
                if(result != null && result.isSuccessful){
                    val accessToken = result.body()?.accessToken
                    if(accessToken != null){
                        _uiState.value = _uiState.value.copy(status = LoadStatus.Success())
                        updateSuccessMessage(result.body()?.message.toString())
                        //Save Token sau khi đăng nhập ở đâu đó
                    }else{
                        _uiState.value = _uiState.value.copy(status = LoadStatus.Error(result.body()?.message.toString()))
                    }
                }

            }catch (ex: Exception){
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error("Sign up request failed"))
            }
        }
    }


}