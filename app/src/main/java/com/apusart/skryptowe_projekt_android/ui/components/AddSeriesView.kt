package com.apusart.skryptowe_projekt_android.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.ExerciseAddRequest
import com.apusart.skryptowe_projekt_android.api.models.SeriesAddRequest
import kotlinx.android.synthetic.main.add_exercise.view.*
import kotlinx.android.synthetic.main.add_series.view.*
import kotlinx.android.synthetic.main.exercise_list_item.view.*

class AddSeriesView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    val view = LayoutInflater.from(context)
        .inflate(R.layout.add_series, this, false)
    private lateinit var commit: () -> Unit
    private val exercisesAdapter = ExercisesAdapter()
    private val exercises = mutableListOf<ExerciseAddRequest>()

    init {
        exercisesAdapter.submitList(exercises)
        ArrayAdapter.createFromResource(
            context,
            R.array.exercises_type,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.add_series_add_exercise.add_exercise_exercise_type.adapter = it
        }

        view.add_series_exercises_list.apply {
            adapter = exercisesAdapter
        }

        view.add_series_add_exercise.add_exercise_button.setOnClickListener {
            val name = view.add_series_add_exercise.add_exercise_name.text
            val number = view.add_series_add_exercise.add_exercise_number.text
            val calories = view.add_series_add_exercise.add_exercise_calories.text
            val about = view.add_series_add_exercise.add_exercise_about.text
            val ytLink = view.add_series_add_exercise.add_exercise_yt_link.text
            val type =
                view.add_series_add_exercise.add_exercise_exercise_type.selectedItem as String

            exercises.add(
                ExerciseAddRequest(
                    name,
                    about,
                    number.toInt(),
                    ytLink,
                    null,
                    type,
                    calories.toInt()
                )
            )
            exercisesAdapter.submitList(exercises)
            exercisesAdapter.notifyDataSetChanged()
            resetExerciseForm()
        }

        view.add_series_add_icon.setOnClickListener {
            commit()
        }

        view.add_series_remove_icon.setOnClickListener {
            resetForm()
        }

        addView(view)
    }

    private fun resetExerciseForm() {
        view.add_series_add_exercise.add_exercise_name.text = ""
        view.add_series_add_exercise.add_exercise_number.text = ""
        view.add_series_add_exercise.add_exercise_calories.text = ""
        view.add_series_add_exercise.add_exercise_about.text = ""
        view.add_series_add_exercise.add_exercise_yt_link.text = ""
        view.add_series_add_exercise.add_exercise_exercise_type.setSelection(0)
        view.add_series_add_exercise.add_exercise_name.requestFocus()
    }

    private fun resetForm() {
        view.add_series_iteration.text = ""
        view.add_series_rest_time.text = ""
        exercises.clear()
        exercisesAdapter.submitList(exercises)
        exercisesAdapter.notifyDataSetChanged()
        resetExerciseForm()
        view.add_series_rest_time.requestFocus()
    }

    fun setUp(f: (SeriesAddRequest) -> Unit) {
        commit = {
//            val list = mutableListOf<ExerciseAddRequest>()
//            list.addAll(exercises)
            f(
                SeriesAddRequest(
                    view.add_series_iteration.text.toInt(),
                    view.add_series_rest_time.text.toInt(),
                    exercises.toList()
                )
            )
            resetForm()
        }
    }
}

class ExercisesAdapter : ListAdapter<ExerciseAddRequest, ExerciseAddRequestViewHolder>(diffUtil) {
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
            return oldItem.name == newItem.name
                    && oldItem.exercise_calories == newItem.exercise_calories
                    && oldItem.number == newItem.number
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

