package com.apusart.skryptowe_projekt_android.ui.logged.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.appComponent
import kotlinx.android.synthetic.main.search.*
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.search) {
    @Inject
    lateinit var viewModel: SearchViewModel

    private lateinit var filterDialog: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupFilters(container)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        search_header.setOnTrailingIconClickListener {
            filterDialog.show()
        }
    }

    private fun setupFilters(container: ViewGroup?) {
        filterDialog = AlertDialog.Builder(requireContext())
        filterDialog
            .setTitle(getString(R.string.sign_out))
            .setView(LayoutInflater.from(requireContext()).inflate(R.layout.filters, container, false))
            .setPositiveButton(R.string.sign_out) { _, _ ->
            }
            .setNegativeButton(R.string.abort) { dialog, _ ->
                dialog.cancel()

            }
    }

}