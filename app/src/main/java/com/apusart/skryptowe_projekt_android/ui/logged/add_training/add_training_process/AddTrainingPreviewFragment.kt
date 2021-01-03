package com.apusart.skryptowe_projekt_android.ui.logged.add_training.add_training_process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.databinding.AddTrainingPreviewBinding
import kotlinx.android.synthetic.main.add_training_preview.*

class AddTrainingPreviewFragment : Fragment() {
    val viewModel: AddTrainingProcessViewModel by activityViewModels()
    private lateinit var seriesAdapter: SeriesAddRequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AddTrainingPreviewBinding =
            DataBindingUtil.inflate(inflater, R.layout.add_training_preview, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesAdapter = SeriesAddRequestAdapter()
        seriesAdapter.submitList(viewModel.series.value)
        add_training_preview_series_list.apply {
            adapter = seriesAdapter
        }

//        viewModel.series.observe(viewLifecycleOwner, {
//            seriesAdapter.submitList(it)
//        })
    }
}