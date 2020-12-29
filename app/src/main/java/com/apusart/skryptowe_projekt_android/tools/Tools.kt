package com.apusart.skryptowe_projekt_android.tools

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContentResolverCompat.query
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.apusart.skryptowe_projekt_android.api.Resource

object Defaults {
    const val baseUrl = "http://10.0.2.2:8000/"
    const val imagesUrl = "http://127.0.0.1:8000/images"
}

object Tools {

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
object Codes {
    const val GET_PHOTO_CODE = 1
}

fun getPathFromUri(uri: Uri, applicationContext: Context): String {

    val projection = Array<String>(1) {
        MediaStore.Images.Media.DATA
    }
    val loader = CursorLoader(applicationContext, uri, projection, null, null, null)
    val cursor = loader.loadInBackground()
    val columnIdx = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val result = columnIdx?.let { cursor.getString(it) }
    cursor?.close()
    return result!!
}
