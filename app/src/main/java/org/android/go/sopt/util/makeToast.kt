package org.android.go.sopt.util

import android.graphics.Color
import android.view.View
import android.widget.Toast

fun View.makeToast(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    Toast.makeText(context, message, duration).show()
}