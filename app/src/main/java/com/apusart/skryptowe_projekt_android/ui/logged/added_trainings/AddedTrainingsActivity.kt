package com.apusart.skryptowe_projekt_android.ui.logged.added_trainings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.TrainingForList
import com.apusart.skryptowe_projekt_android.api.models.TrainingSafeArg
import com.apusart.skryptowe_projekt_android.api.models.handleResource
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.ui.logged.trainings.TrainingsFragmentDirections
import kotlinx.android.synthetic.main.added_trainings.*
import kotlinx.android.synthetic.main.training_list_item.view.*
import javax.inject.Inject

class AddedTrainingsActivity: AppCompatActivity(R.layout.added_trainings) {

    @Inject
    lateinit var viewModel: AddedTrainingsViewModel

    lateinit var trainingsAdapter: TrainingsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        trainingsAdapter = TrainingsAdapter()

        added_trainings_list.apply {
            adapter = trainingsAdapter
        }

        setUpObservers()
        added_trainings_header.setOnLeadingIconClickListener {
            finish()
        }
    }

    private fun setUpObservers() {
        viewModel.trainings.observe(this, { res ->
            handleResource(res,
                onSuccess = {
                    trainingsAdapter.submitList(it)
                })
        })
    }
}

class TrainingsAdapter : ListAdapter<TrainingForList, TrainingForListViewHolder>(diffUtil) {
    object diffUtil : DiffUtil.ItemCallback<TrainingForList>() {
        override fun areItemsTheSame(oldItem: TrainingForList, newItem: TrainingForList): Boolean {
            return oldItem.training_id == newItem.training_id
        }

        override fun areContentsTheSame(
            oldItem: TrainingForList,
            newItem: TrainingForList
        ): Boolean {
            return oldItem.training_id == newItem.training_id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingForListViewHolder {
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.training_list_item, parent, false)

        return TrainingForListViewHolder(container)
    }

    override fun onBindViewHolder(holder: TrainingForListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TrainingForListViewHolder(container: View) : RecyclerView.ViewHolder(container) {
    fun bind(training: TrainingForList) {
        itemView.apply {
            training_list_item_training_type.text = training.training_type
            training_list_item_training_title.text = training.name
            training_list_item_training_creator.text = training.created_by.nick
            training_list_item_training_calories.text =
                resources.getString(R.string.training_calories, training.training_calories)
        }
    }
}