package com.apusart.skryptowe_projekt_android.ui.logged.search

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.databinding.FiltersBinding
import com.apusart.skryptowe_projekt_android.tools.getSelectedTrainingType
import kotlinx.android.synthetic.main.add_exercise.view.*
import kotlinx.android.synthetic.main.add_series.view.*
import kotlinx.android.synthetic.main.filters.*
import javax.inject.Inject

class FiltersDialogFragment: AppCompatDialogFragment() {

    val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        requireActivity().appComponent.inject(this)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FiltersBinding = DataBindingUtil.inflate(inflater, R.layout.filters, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filters_name.text = viewModel.filters.value?.trainingName ?: ""
        filters_calories_min.text = viewModel.filters.value?.trainingCaloriesMin?.toString() ?: ""
        filters_calories_max.text = viewModel.filters.value?.trainingCaloriesMax?.toString() ?: ""
        filters_type.setSelection(getSelectedTrainingType(viewModel.filters.value?.trainingType))

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.training_type,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            filters_type.adapter = it
        }

        filters_apply.setOnClickListener {
            val name = filters_name.text
            val calMin = if (filters_calories_min.text == "") null else filters_calories_min.text.toInt()
            val calMax = if (filters_calories_max.text == "") null else filters_calories_max.text.toInt()
            val type = if (filters_type.selectedItem as String == "ALL") null else filters_type.selectedItem as String

            viewModel.commitFilters(Filters(name, calMin, calMax, type))
            viewModel.getTrainings()
            dismiss()
        }
    }
}