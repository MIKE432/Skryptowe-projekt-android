package com.apusart.evently_android.guest.initial_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apusart.skryptowe_projekt_android.api.User
import com.apusart.skryptowe_projekt_android.api.repositories.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import javax.inject.Inject

class InitialActivityViewModel @Inject constructor(userRepository: UserRepository): ViewModel() {
    val currentUser = MutableLiveData<User>(null)
}