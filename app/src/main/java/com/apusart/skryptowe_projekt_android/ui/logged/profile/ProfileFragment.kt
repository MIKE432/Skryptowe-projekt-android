package com.apusart.skryptowe_projekt_android.ui.logged.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.handleResource
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.tools.Defaults
import com.apusart.skryptowe_projekt_android.ui.guest.initial_activity.InitialActivity
import kotlinx.android.synthetic.main.profile.*
import javax.inject.Inject

class ProfileFragment : Fragment(R.layout.profile) {

    @Inject
    lateinit var viewModel: ProfileFragmentViewModel
    private lateinit var logoutAlertDialog: AlertDialog.Builder
    private lateinit var deleteUserDialog: AlertDialog.Builder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)


        setupDialogs()
        setupOnClickListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner, { res ->
            handleResource(res,
                onSuccess = {
                    profile_page_user_name.text = "${it?.name} ${it?.surname}"
                    if (it?.avatar != null)
                        profile_page_user_picture.loadPhoto(Defaults.imagesUrl + it.avatar)
                }, onPending = {

                }, onError = { _, _ ->

                })
        })

        viewModel.isLoggedOut.observe(viewLifecycleOwner, { res ->
            handleResource(res,
                onSuccess = {
                    startActivity(
                        Intent(requireContext(), InitialActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    requireActivity().finishAffinity()
                }, onPending = {

                }, onError = { _, _ ->

                })
        })

        viewModel.isDeleted.observe(viewLifecycleOwner, { res ->
            handleResource(res,
            onSuccess = {
                if (it == true) {
                    startActivity(Intent(requireActivity(), InitialActivity::class.java))
                    requireActivity().finishAffinity()
                }

            })
        })
    }

    private fun setupOnClickListeners() {
        profile_page_logout_button.setOnClickListener {
            logoutAlertDialog.show()
        }

        profile_page_added_trainings_button.setOnClickListener {
            findNavController().navigate(R.id.addedTrainingsActivity)
        }

        profile_page_remove_profile.setOnClickListener {
            deleteUserDialog.show()
        }
    }

    private fun setupDialogs() {

        deleteUserDialog = AlertDialog.Builder(requireContext())
        deleteUserDialog
            .setTitle(getString(R.string.delete_account))
            .setMessage(getString(R.string.delete_account_for_sure))
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteUser()
            }
            .setNegativeButton(R.string.abort) { dialog, _ ->
                dialog.cancel()
            }


        logoutAlertDialog = AlertDialog.Builder(requireContext())
        logoutAlertDialog
            .setTitle(getString(R.string.sign_out))
            .setMessage(getString(R.string.wanna_sign_out))
            .setPositiveButton(R.string.sign_out) { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton(R.string.abort) { dialog, _ ->
                dialog.cancel()
            }
    }
}