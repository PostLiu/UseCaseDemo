package com.postliu.usecasedemo.util

import android.content.Context


/**
 * 通过包名启动App
 *
 * @param packageName 需要启动的App的应用包名
 */
fun Context.launchByPackageName(packageName: String) = kotlin.runCatching {
    packageManager.getLeanbackLaunchIntentForPackage(packageName).run {
        startActivity(this)
    }
}