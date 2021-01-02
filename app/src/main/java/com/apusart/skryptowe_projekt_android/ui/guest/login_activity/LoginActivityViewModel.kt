package com.apusart.skryptowe_projekt_android.ui.guest.login_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.User
import com.apusart.skryptowe_projekt_android.api.repositories.UserRepository
import kotlinx.coroutines.launch

import java.lang.Exception
import javax.inject.Inject

class LoginActivityViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val nickText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()
    val user = MutableLiveData<Resource<User>>()

    fun uploadImage(filePath: String) {
        viewModelScope.launch {
            userRepository.uploadPhoto(filePath)
        }
    }

    fun logIn() {
        viewModelScope.launch {
            try {

                if (nickText.value == null || nickText.value.equals("")) {
                    return@launch
                }

                if (passwordText.value == null || passwordText.value.equals("")) {
                    return@launch
                }

                user.value = Resource.pending()
                user.value = userRepository.loginUser(nickText.value!!, passwordText.value!!)
            } catch (e: Exception) {
                user.value = Resource.error(e.message)
            }
        }
    }
}