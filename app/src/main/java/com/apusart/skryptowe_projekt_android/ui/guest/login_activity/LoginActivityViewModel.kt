package com.apusart.evently_android.guest.login_activity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.skryptowe_projekt_android.api.Resource
import com.apusart.skryptowe_projekt_android.api.User
import com.apusart.skryptowe_projekt_android.api.repositories.UserRepository
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

import java.lang.Exception
import javax.inject.Inject

class LoginActivityViewModel @Inject constructor(userRepository: UserRepository): ViewModel() {
    val emailText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()
    val user = MutableLiveData<Resource<User>>()

    fun logIn() {
        viewModelScope.launch {
            try {

                if (emailText.value == null || emailText.value.equals("")) {
                    return@launch
                }

                if (passwordText.value == null || passwordText.value.equals("")) {
                    return@launch
                }

                user.value = Resource.pending()
//                user.value = userRepository.emailLogIn(emailText.value!!, passwordText.value!!)
            } catch (e: Exception) {
                user.value = Resource.error(e.message)
            }
        }
    }
}