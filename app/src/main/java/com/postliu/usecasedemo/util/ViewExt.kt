package com.postliu.usecasedemo.util

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.postliu.usecasedemo.Event

fun View.showSnackbar(content: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, content, duration).run {
        show()
    }
}

fun View.setupSnackbar(
    owner: LifecycleOwner,
    event: LiveData<Event<Int>>,
    duration: Int = Snackbar.LENGTH_SHORT
) {

    event.observe(owner) {
        it.getContentIfNotHandled()?.let {
            showSnackbar(context.getString(it), duration)
        }
    }
}