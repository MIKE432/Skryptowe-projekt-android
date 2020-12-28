package com.apusart.skryptowe_projekt_android.ui.guest.initial_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.apusart.evently_android.guest.initial_activity.InitialActivityViewModel
import com.apusart.skryptowe_projekt_android.api.local_data_source.GymDatabase
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.ui.guest.login_activity.LoginActivity
import com.apusart.skryptowe_projekt_android.ui.logged.main.MainLoggedActivity
import javax.inject.Inject

class InitialActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModel: InitialActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        GymDatabase.initialize(applicationContext)
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel.currentUser.observe(this, {
            if(it == null) {
                startActivity(Intent(applicationContext, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))

            } else {
                startActivity(Intent(applicationContext, MainLoggedActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))

            }
        })
    }
}