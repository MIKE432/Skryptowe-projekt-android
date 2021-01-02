package com.apusart.skryptowe_projekt_android.ui.guest.register_activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apusart.skryptowe_projekt_android.R
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.handleResource
import com.apusart.skryptowe_projekt_android.appComponent
import com.apusart.skryptowe_projekt_android.databinding.RegisterBinding
import com.apusart.skryptowe_projekt_android.tools.Tools
import com.apusart.skryptowe_projekt_android.ui.guest.login_activity.LoginActivity
import com.apusart.skryptowe_projekt_android.ui.logged.main.MainLoggedActivity

import kotlinx.android.synthetic.main.register.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: RegisterActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val binding: RegisterBinding = DataBindingUtil.setContentView(this, R.layout.register)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        register_email_register_button.setOnClickListener {
            Tools.hideKeyboard(this)
            viewModel.register()
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
                    register_email_register_button.transitionToEnd()
                },
                onError = { msg, _ ->
                    register_email_register_button.transitionToStart()
                    register_error_modal.isActive = true
                    register_error_modal.modalInformation = msg
                })
        })

        register_register_text.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            )
        }
    }
}