package com.apusart.skryptowe_projekt_android.ui.logged.add_training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.tools.TrainingTypes
import kotlinx.android.synthetic.main.add_training.*
import kotlinx.android.synthetic.main.training_type_item.view.*

class AddTrainingFragment: Fragment(R.layout.add_training) {
    private lateinit var trainingTypesAdapter: TrainingTypesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trainingTypesAdapter = TrainingTypesAdapter()
        trainingTypesAdapter.submitList(TrainingTypes.values().toMutableList())
        add_training_training_types_list.apply {
            adapter = trainingTypesAdapter
        }
    }
}

class TrainingTypesAdapter: ListAdapter<TrainingTypes, TrainingTypesViewHolder>(diffUtil) {
    object diffUtil: DiffUtil.ItemCallback<TrainingTypes>() {
        override fun areItemsTheSame(oldItem: TrainingTypes, newItem: TrainingTypes): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TrainingTypes, newItem: TrainingTypes): Boolean {
            return oldItem.name == newItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingTypesViewHolder {
        val container = LayoutInflater.from(parent.context)
            .inflate(R.layout.training_type_item, parent, false)

        return TrainingTypesViewHolder(container)
    }

    override fun onBindViewHolder(holder: TrainingTypesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TrainingTypesViewHolder(container: View): RecyclerView.ViewHolder(container) {
    fun bind(trainingType: TrainingTypes) {
        itemView.apply {
            training_type_item_title.text = trainingType.trainingType.type
            training_type_item_about.text = trainingType.trainingType.about
            setOnClickListener {
                findNavController().navigate(AddTrainingFragmentDirections.actionAddTrainingFragmentToAddTrainingProcessActivity(trainingType.name))
            }
        }
    }
}