package com.apusart.skryptowe_projekt_android.ui.logged.trainings.training_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.Exercise
import com.apusart.skryptowe_projekt_android.api.models.ExerciseSafeArg
import com.apusart.skryptowe_projekt_android.api.models.Series
import com.apusart.skryptowe_projekt_android.api.models.handleResource
import com.apusart.skryptowe_projekt_android.appComponent
import kotlinx.android.synthetic.main.exercise_list_item.view.*
import kotlinx.android.synthetic.main.series_list_item.view.*
import kotlinx.android.synthetic.main.training_details.*
import javax.inject.Inject

class TrainingDetails : Fragment(R.layout.training_details) {
    @Inject
    lateinit var viewModel: TrainingDetailsViewModel
    lateinit var seriesAdapter: SeriesAdapter
    private val navArgs by navArgs<TrainingDetailsArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        training_details_header.title = navArgs.training.name
        training_details_header.subtitle = navArgs.training.creator_nick
        training_details_about.text = navArgs.training.about
        training_details_header.setOnLeadingIconClickListener {
            requireActivity().onBackPressed()
        }

        seriesAdapter = SeriesAdapter()
        training_details_series_list.apply {
            adapter = seriesAdapter
        }

        viewModel.trainingDetails.observe(viewLifecycleOwner, { res ->
            handleResource(res,
                onSuccess = {
                    seriesAdapter.submitList(it?.series)
                }, onPending = {

                }, onError = { _, _ ->

                })
        })

        viewModel.getTrainingById(navArgs.training.training_id)
    }

}

class SeriesAdapter : ListAdapter<Series, SeriesViewHolder>(diffUtil) {
    object diffUtil : DiffUtil.ItemCallback<Series>() {
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.series_id == newItem.series_id
        }

        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.series_id == newItem.series_id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.series_list_item, parent, false)

        return SeriesViewHolder(container)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SeriesViewHolder(container: View) : RecyclerView.ViewHolder(container) {

    fun bind(series: Series) {
        val exercisesAdapter = ExercisesAdapter()
        itemView.apply {
            series_list_item_rest_time.text =
                resources.getString(R.string.rest_time_in_seconds, series.rest_time)
            series_list_item_iteration.text =
                resources.getString(R.string.iteration, series.iteration)

            training_details_exercise_list.adapter = exercisesAdapter
            training_details_exercise_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            exercisesAdapter.submitList(series.exercises)
        }
    }
}

class ExercisesAdapter : ListAdapter<Exercise, ExerciseViewHolder>(diffUtil) {
    object diffUtil : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.exercise_id == newItem.exercise_id
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.exercise_id == newItem.exercise_id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_list_item, parent, false)

        return ExerciseViewHolder(container)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ExerciseViewHolder(container: View) : RecyclerView.ViewHolder(container) {
    fun bind(exercise: Exercise) {
        itemView.apply {
            exercise_list_item_name.text = exercise.name
            exercise_list_item_calories.text =
                resources.getString(R.string.calories_about, exercise.exercise_calories)
            exercise_list_item_number.text =
                resources.getString(R.string.number_exercise, exercise.number)
            setOnClickListener {
                findNavController().navigate(TrainingDetailsDirections.actionTrainingDetailsToExerciseDetailsFragment(
                    ExerciseSafeArg(exercise.name, exercise.about, exercise.yt_link, exercise.photo, exercise.exercise_type)
                ))
            }
        }
    }
}