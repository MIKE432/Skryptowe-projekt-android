package com.apusart.skryptowe_projekt_android.ui.logged.trainings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.TrainingForList
import com.apusart.skryptowe_projekt_android.api.models.TrainingSafeArg
import com.apusart.skryptowe_projekt_android.api.models.handleResource
import com.apusart.skryptowe_projekt_android.appComponent
import kotlinx.android.synthetic.main.training_list_item.view.*
import kotlinx.android.synthetic.main.trainings.*
import javax.inject.Inject

class TrainingsFragment : Fragment(R.layout.trainings) {
    @Inject
    lateinit var viewModel: TrainingsFragmentViewModel

    private lateinit var trainingsAdapter: TrainingsAdapter
    private lateinit var filterDialog: AlertDialog.Builder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)



        trainingsAdapter = TrainingsAdapter()
        trainings_list.apply {
            adapter = trainingsAdapter
        }

        viewModel.trainings.observe(viewLifecycleOwner, { res ->
            handleResource(res,
                onSuccess = {
                    trainingsAdapter.submitList(it)
                },
                onPending = {

                }, onError = { _, _ ->

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
            setOnClickListener {
                findNavController().navigate(TrainingsFragmentDirections.actionTrainingsFragmentToTrainingDetails(
                    TrainingSafeArg(training.training_id, training.created_by.nick, training.name)
                ))
            }
        }
    }
}