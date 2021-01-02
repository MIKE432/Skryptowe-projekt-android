package com.apusart.skryptowe_projekt_android.ui.logged.trainings.training_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.tools.Defaults
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.exercise_details.*

class ExerciseDetailsFragment: Fragment(R.layout.exercise_details) {
    private val navArgs by navArgs<ExerciseDetailsFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exercise_details_header.title = navArgs.exercise.name
        exercise_details_header.setOnLeadingIconClickListener {
            requireActivity().onBackPressed()
        }


        exercise_details_exercise_type.text = navArgs.exercise.exercise_type
        exercise_details_about.text = navArgs.exercise.about
        exercise_details_yt_link.isVisible = navArgs.exercise.yt_link != "" &&  URLUtil.isValidUrl(navArgs.exercise.yt_link)

        exercise_details_yt_link.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(navArgs.exercise.yt_link)))
        }

        if (navArgs.exercise.photo != null)
            Glide.with(this)
                .load(Defaults.imagesUrl + navArgs.exercise.photo)
                .centerCrop()
                .into(exercise_details_photo)
    }
}