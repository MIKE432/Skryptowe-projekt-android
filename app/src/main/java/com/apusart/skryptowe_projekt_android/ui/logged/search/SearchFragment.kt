package com.apusart.skryptowe_projekt_android.ui.logged.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.TrainingForList
import com.apusart.skryptowe_projekt_android.api.models.TrainingSafeArg
import com.apusart.skryptowe_projekt_android.api.models.handleResource
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.ui.logged.trainings.TrainingsFragmentDirections
import kotlinx.android.synthetic.main.search.*
import kotlinx.android.synthetic.main.training_list_item.view.*
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.search) {

    val viewModel: SearchViewModel by activityViewModels()

    lateinit var trainingsAdapter: TrainingsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        trainingsAdapter = TrainingsAdapter()

        viewModel.trainings.observe(viewLifecycleOwner, { res ->
            val x = 10
            handleResource(res,
                onSuccess = {
                    trainingsAdapter.submitList(it)
                })
        })
        search_trainings_list.apply {
            adapter = trainingsAdapter
        }

        search_header.setOnTrailingIconClickListener {
            openDialog()
        }
        viewModel.getTrainings()
    }

    private fun openDialog() {
        val filtersDialog = FiltersDialogFragment()
        filtersDialog.show(parentFragmentManager, "Dialog")
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
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToTrainingDetails(
                        TrainingSafeArg(
                            training.training_id,
                            training.created_by.nick,
                            training.name,
                            training.about
                        )
                    )
                )
            }
        }
    }
}