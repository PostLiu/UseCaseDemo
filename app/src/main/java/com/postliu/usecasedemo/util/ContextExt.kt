package com.postliu.usecasedemo.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.TypedValue


/**
 * 通过包名启动App
 *
 * @param packageName 需要启动的App的应用包名
 */
fun Context.launchByPackageName(packageName: String) = kotlin.runCatching {
    packageManager.getLaunchIntentForPackage(packageName).run {
        startActivity(this)
    }
}

fun Context.uninstallSoftware(packageName: String) = kotlin.runCatching {
    startActivity(Intent(Intent.ACTION_DELETE, Uri.fromParts("package", packageName, null)))
}

fun Context.dpToPx(dp: Float) = with(resources) {
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
}