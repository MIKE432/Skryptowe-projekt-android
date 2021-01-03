package com.apusart.skryptowe_projekt_android.ui.logged.add_training.add_training_process

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.databinding.AddTrainingDetailsBinding
import javax.inject.Inject

class AddTrainingDetailsFragment: Fragment() {

    private val viewModel: AddTrainingProcessViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AddTrainingDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.add_training_details, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}