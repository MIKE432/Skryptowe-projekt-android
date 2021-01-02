package com.apusart.skryptowe_projekt_android.ui.guest.initial_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apusart.skryptowe_projekt_android.api.models.User
import com.apusart.skryptowe_projekt_android.api.repositories.UserRepository

import javax.inject.Inject

class InitialActivityViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    val currentUser = userRepository.getCurrentUser()
}