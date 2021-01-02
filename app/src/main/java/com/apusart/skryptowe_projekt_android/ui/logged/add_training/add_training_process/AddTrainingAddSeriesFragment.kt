package com.apusart.skryptowe_projekt_android.ui.logged.add_training.add_training_process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.*
import com.apusart.skryptowe_projekt_android.ui.logged.trainings.training_details.ExercisesAdapter
import com.apusart.skryptowe_projekt_android.ui.logged.trainings.training_details.SeriesAdapter
import com.apusart.skryptowe_projekt_android.ui.logged.trainings.training_details.TrainingDetailsDirections
import kotlinx.android.synthetic.main.add_training_series.*
import kotlinx.android.synthetic.main.exercise_list_item.view.*
import kotlinx.android.synthetic.main.series_list_item.view.*

class AddTrainingAddSeriesFragment : Fragment(R.layout.add_training_series) {

    private val viewModel: AddTrainingProcessViewModel by activityViewModels()
    private lateinit var seriesAdapter: SeriesAddRequestAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesAdapter = SeriesAddRequestAdapter()

        add_training_series_series_list.apply {
            adapter = seriesAdapter
        }

        viewModel.series.observe(viewLifecycleOwner, {
            seriesAdapter.submitList(it)
        })

        add_training_series_series_form.setUp {
            viewModel.addSeries(it)
        }
    }
}

class SeriesAddRequestAdapter :
    ListAdapter<SeriesAddRequest, SeriesAddRequestViewHolder>(diffUtil) {
    object diffUtil : DiffUtil.ItemCallback<SeriesAddRequest>() {
        override fun areItemsTheSame(
            oldItem: SeriesAddRequest,
            newItem: SeriesAddRequest
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SeriesAddRequest,
            newItem: SeriesAddRequest
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesAddRequestViewHolder {
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.series_list_item, parent, false)

        return SeriesAddRequestViewHolder(container)
    }

    override fun onBindViewHolder(holder: SeriesAddRequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SeriesAddRequestViewHolder(container: View) : RecyclerView.ViewHolder(container) {

    fun bind(series: SeriesAddRequest) {
        val exercisesAdapter = ExerciseAddRequestAdapter()
        itemView.apply {
            series_list_item_rest_time.text =
                resources.getString(R.string.rest_time_in_seconds, series.rest_time)
            series_list_item_iteration.text =
                resources.getString(R.string.iteration, series.iteration)

            training_details_exercise_list.adapter = exercisesAdapter
            training_details_exercise_list.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            exercisesAdapter.submitList(series.exercises)
        }
    }
}

class ExerciseAddRequestAdapter :
    ListAdapter<ExerciseAddRequest, ExerciseAddRequestViewHolder>(diffUtil) {
    object diffUtil : DiffUtil.ItemCallback<ExerciseAddRequest>() {
        override fun areItemsTheSame(
            oldItem: ExerciseAddRequest,
            newItem: ExerciseAddRequest
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ExerciseAddRequest,
            newItem: ExerciseAddRequest
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseAddRequestViewHolder {
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_list_item, parent, false)

        return ExerciseAddRequestViewHolder(container)
    }

    override fun onBindViewHolder(holder: ExerciseAddRequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ExerciseAddRequestViewHolder(container: View) : RecyclerView.ViewHolder(container) {
    fun bind(exercise: ExerciseAddRequest) {
        itemView.apply {
            exercise_list_item_name.text = exercise.name
            exercise_list_item_calories.text =
                resources.getString(R.string.calories_about, exercise.exercise_calories)
            exercise_list_item_number.text =
                resources.getString(R.string.number_exercise, exercise.number)
        }
    }
}