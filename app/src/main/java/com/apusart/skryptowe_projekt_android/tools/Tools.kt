package com.apusart.skryptowe_projekt_android.tools

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.apusart.skryptowe_projekt_android.api.Resource

object Defaults {
    const val baseUrl = ""
    const val imagesUrl = ""
}

object Tools {
    //to do


    fun hideKeyboard(fragment: Fragment?) {
        val view = fragment?.requireActivity()?.currentFocus
        view?.let { v ->
            val imm = fragment.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun hideKeyboard(activity: Activity?) {
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}

