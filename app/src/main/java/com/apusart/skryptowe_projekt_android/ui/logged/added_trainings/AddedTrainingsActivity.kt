package com.apusart.skryptowe_projekt_android.ui.logged.added_trainings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.TrainingForList
import com.apusart.skryptowe_projekt_android.api.models.handleResource
import com.apusart.skryptowe_projekt_android.appComponent
import kotlinx.android.synthetic.main.added_trainings.*
import kotlinx.android.synthetic.main.training_list_item.view.*
import javax.inject.Inject

class AddedTrainingsActivity : AppCompatActivity(R.layout.added_trainings) {

    @Inject
    lateinit var viewModel: AddedTrainingsViewModel

    lateinit var trainingsAdapter: TrainingsAdapter
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        alertDialog = AlertDialog.Builder(this)
        trainingsAdapter = TrainingsAdapter(alertDialog, this, viewModel::deleteTraining)

        itemTouchHelper = ItemTouchHelper(SingleItemCallback(trainingsAdapter))

        added_trainings_list.apply {
            adapter = trainingsAdapter
        }

        itemTouchHelper.attachToRecyclerView(added_trainings_list)
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

        viewModel.deletedTrainings.observe(this, { res ->
            if (res.isEmpty())
                return@observe

            handleResource(res.last(),
                onSuccess = {
                    trainingsAdapter.removeTraining(it)
                    trainingsAdapter.notifyDataSetChanged()
                })
        })
    }
}

class TrainingsAdapter(
    private val alertDialog: AlertDialog.Builder,
    private val context: Context,
    private val deleteFunc: (Int) -> Unit
) : ListAdapter<TrainingForList, TrainingForListViewHolder>(diffUtil) {

    init {
        alertDialog
            .setTitle("Usunąć trening?")
            .setMessage("Czy na pewno chcesz usunąć trening?")
            .setNegativeButton("Anuluj") { dialog, _ ->
                dialog.cancel()
            }

    }

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

    fun deleteTraining(position: Int) {
        alertDialog
            .setOnCancelListener { notifyDataSetChanged() }
            .setPositiveButton("Usuń") { _, _ ->
                deleteFunc(getItem(position).training_id)
            }.create().show()
    }

    fun removeTraining(trainingId: Int?) {
        submitList(currentList.filter { it.training_id != trainingId })
        notifyDataSetChanged()
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

class SingleItemCallback(private val adapter: TrainingsAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.deleteTraining(viewHolder.layoutPosition)
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