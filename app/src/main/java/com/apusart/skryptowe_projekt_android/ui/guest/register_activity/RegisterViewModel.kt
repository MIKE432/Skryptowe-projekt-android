package com.apusart.skryptowe_projekt_android.ui.guest.register_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.User
import com.apusart.skryptowe_projekt_android.api.repositories.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class RegisterActivityViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val nickText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()
    val nameText = MutableLiveData<String>()
    val surnameText = MutableLiveData<String>()
    val user = MutableLiveData<Resource<User>>()

    fun register() {
        viewModelScope.launch {
            try {
                if (nickText.value == null || nickText.value.equals("")) {
                    return@launch
                }

                if (passwordText.value == null || passwordText.value.equals("")) {
                    return@launch
                }
                user.value = Resource.pending()
                user.value = userRepository.registerUser(nickText.value!!, nameText.value!!, surnameText.value!!, passwordText.value!!)
            } catch (e: Exception) {
                user.value = Resource.error(e.message)
            }
        }
    }
}