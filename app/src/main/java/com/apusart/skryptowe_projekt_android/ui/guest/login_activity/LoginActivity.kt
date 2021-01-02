package com.apusart.skryptowe_projekt_android.ui.guest.login_activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.handleResource
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.databinding.LoginBinding
import com.apusart.skryptowe_projekt_android.tools.Codes
import com.apusart.skryptowe_projekt_android.tools.Tools
import com.apusart.skryptowe_projekt_android.tools.getPathFromUri
import com.apusart.skryptowe_projekt_android.ui.guest.register_activity.RegisterActivity
import com.apusart.skryptowe_projekt_android.ui.logged.main.MainLoggedActivity
import kotlinx.android.synthetic.main.login.*
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val binding: LoginBinding = DataBindingUtil.setContentView(this, R.layout.login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        login_email_login_button.setOnClickListener {
            Tools.hideKeyboard(this)
            viewModel.logIn()
        }

        login_forgot_password.setOnClickListener {
            Intent(
                Intent.ACTION_PICK
            ).also {
                it.type = "image/jpg"
                startActivityForResult(it, Codes.GET_PHOTO_CODE)
            }
        }

        login_register_text.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            )
        }

        viewModel.user.observe(this, { res ->
            handleResource(res,
                onSuccess = {
                    startActivity(
                        Intent(this, MainLoggedActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                },
                onPending = {
                    login_email_login_button.transitionToEnd()
                },
                onError = { msg, _ ->
                    login_email_login_button.transitionToStart()
                    login_error_modal.modalInformation = msg
                    login_error_modal.isActive = true
                })
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Codes.GET_PHOTO_CODE) {
            val imageUri = data?.data
            if (imageUri != null)
                viewModel.uploadImage(getPathFromUri(imageUri, applicationContext))
        }
    }
}