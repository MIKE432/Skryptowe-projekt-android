package com.apusart.skryptowe_projekt_android.ui.logged.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.ui.logged.search.SearchViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_logged.*
import javax.inject.Inject

class MainLoggedActivity : AppCompatActivity(R.layout.main_logged) {

    val viewModel: SearchViewModel by viewModels()

    override fun onBackPressed() {
        val navController = findNavController(R.id.main_logged_fragment_container)
        when (navController.currentDestination?.id) {
            R.id.trainingsFragment -> {
                val startMain = Intent(Intent.ACTION_MAIN)
                startMain.addCategory(Intent.CATEGORY_HOME)
                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(startMain)
            }
            R.id.searchFragment -> {

                main_logged_navigation_view.selectedItemId = R.id.trainings_page
            }
            R.id.addTrainingFragment -> {

                main_logged_navigation_view.selectedItemId = R.id.trainings_page
            }
            R.id.profileFragment -> {

                main_logged_navigation_view.selectedItemId = R.id.trainings_page
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController(R.id.main_logged_fragment_container).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.trainingsFragment -> {
                    main_logged_navigation_view.isVisible = true
                }
                R.id.searchFragment -> {
                    main_logged_navigation_view.isVisible = true
                }
                R.id.addTrainingFragment -> {
                    main_logged_navigation_view.isVisible = true
                }
                R.id.profileFragment -> {
                    main_logged_navigation_view.isVisible = true
                }
                else -> {
                    main_logged_navigation_view.isVisible = false
                }
            }
        }
        main_logged_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.trainings_page -> {
                    findNavController(R.id.main_logged_fragment_container)
                        .navigate(R.id.trainingsFragment)
                    true
                }
                R.id.search_page -> {
                    findNavController(R.id.main_logged_fragment_container)
                        .navigate(R.id.searchFragment)
                    true
                }
                R.id.add_training_page -> {
                    findNavController(R.id.main_logged_fragment_container)
                        .navigate(R.id.addTrainingFragment)
                    true
                }
                R.id.profile_page -> {
                    findNavController(R.id.main_logged_fragment_container)
                        .navigate(R.id.profileFragment)
                    true
                }

                else -> false
            }
        }


    }
}