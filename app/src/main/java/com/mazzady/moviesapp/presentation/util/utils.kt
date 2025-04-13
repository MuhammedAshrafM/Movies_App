package com.mazzady.moviesapp.presentation.util

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.showSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionText: String? = null,
    action: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, duration)
    actionText?.let {
        snackbar.setAction(it) { action?.invoke() }
    }
    snackbar.show()
}