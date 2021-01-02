package com.apusart.skryptowe_projekt_android.ui.logged.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.repositories.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ProfileFragmentViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val user = userRepository.getUser()
    val isLoggedOut = MutableLiveData<Resource<Boolean>>()

    fun logout() {
        viewModelScope.launch {
            try {
                isLoggedOut.value = Resource.pending()
                isLoggedOut.value = userRepository.logout()
            } catch (e: Exception) {
                isLoggedOut.value = Resource.error(e.message)
            }
        }
    }
}