package com.example.player.utils.extension

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.shortToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}


fun Fragment.shortToast(@StringRes text: Int) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}


fun Fragment.longToast(@StringRes text: Int) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}