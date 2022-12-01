package com.postliu.usecasedemo.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import com.postliu.usecasedemo.data.Software

object SoftwareUtils {

    private const val TAG = "SoftwareUtils"

    @Suppress("DEPRECATION")
    fun getInstalledPackages(context: Context): List<Software> {
        val pm = context.packageManager
        return pm.getInstalledPackages(PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES)
            .asSequence().map {
                val label = it.applicationInfo.loadLabel(pm).toString()
                val packageName = it.packageName.orEmpty()
                val versionName = it.versionName.orEmpty()
                val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    it.longVersionCode
                } else {
                    it.versionCode
                }.toString()
                val isSystemSoftware = isSystemApp(it)
                Software(
                    labelName = label,
                    packageName = packageName,
                    versionName = versionName,
                    versionCode = versionCode,
                    systemFrom = isSystemSoftware
                )
            }.toList()
    }

    fun isSystemApp(info: PackageInfo): Boolean {
        return (info.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 1
    }

    fun getPackageIcon(context: Context, packageName: String): Drawable {
        val pm = context.packageManager
        return pm.getApplicationIcon(packageName)
    }
}