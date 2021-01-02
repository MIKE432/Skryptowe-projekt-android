package com.apusart.skryptowe_projekt_android.ui.logged.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
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
    private lateinit var alertDialog: AlertDialog.Builder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

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

        alertDialog = AlertDialog.Builder(requireContext())
        alertDialog
            .setTitle(getString(R.string.sign_out))
            .setMessage(getString(R.string.wanna_sign_out))
            .setPositiveButton(R.string.sign_out) { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton(R.string.abort) { dialog, _ ->
                dialog.cancel()
            }


        profile_page_logout_button.setOnClickListener {
            alertDialog.show()
        }

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
    }
}