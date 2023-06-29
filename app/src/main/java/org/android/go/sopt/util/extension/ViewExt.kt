package org.android.go.sopt.util.extension

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.util.OnSingleClickListener

fun View.makeSnackBar(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    Snackbar.make(this, message, duration)
        .setBackgroundTint(Color.WHITE)
        .setTextColor(Color.BLACK)
        .show()
}

fun View.makeToast(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    Toast.makeText(context, message, duration).show()
}

inline fun View.setOnSingleClickListener(crossinline onSingleClick: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener { onSingleClick(it) })
}