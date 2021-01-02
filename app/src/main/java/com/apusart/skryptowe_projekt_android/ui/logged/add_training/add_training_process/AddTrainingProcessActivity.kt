package com.apusart.skryptowe_projekt_android.ui.logged.add_training.add_training_process

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.databinding.AddTrainingProcessBinding
import com.apusart.skryptowe_projekt_android.databinding.RegisterBinding
import com.apusart.skryptowe_projekt_android.ui.logged.trainings.training_details.TrainingDetailsArgs
import kotlinx.android.synthetic.main.add_training_process.*
import javax.inject.Inject

class AddTrainingProcessActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: AddTrainingProcessViewModel
    private val destinations = listOf(
        AddTrainingDestination("Training Details", "", R.id.addTrainingDetailsFragment, "Add Series"),
        AddTrainingDestination("Add Series", "", R.id.addTrainingAddSeriesFragment, "Preview"),
        AddTrainingDestination("Preview", "", R.id.addTrainingPreviewFragment, "Add training")
    )

    private val navArgs by navArgs<AddTrainingProcessActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        val binding: AddTrainingProcessBinding =
            DataBindingUtil.setContentView(this, R.layout.add_training_process)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.step.observe(this, {
            setupNewView(it)
        })

        add_training_process_header.setOnLeadingIconClickListener {
            viewModel.decrement()
        }

        add_training_process_progress_button.setOnClickListener {
            viewModel.increment()
        }
    }

    private fun setupNewView(step: Int) {
        val navController = findNavController(R.id.add_training_process_fragment_container)
        navController.navigate(destinations[step].destination, bundleOf("trainingType" to navArgs.trainingType))
        add_training_process_header.title = destinations[step].title
        add_training_process_header.subtitle = destinations[step].subtitle
        add_training_process_progress_button.title = destinations[step].buttonText
        add_training_process_header.title = destinations[step].title
    }
}

data class AddTrainingDestination(
    val title: String,
    val subtitle: String,
    val destination: Int,
    val buttonText: String
)