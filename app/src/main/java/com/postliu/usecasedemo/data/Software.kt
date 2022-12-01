package com.postliu.usecasedemo.data

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "software")
data class Software(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    @Ignore
    var icon: Drawable? = null,
    var labelName: String,
    var packageName: String,
    var versionName: String,
    var versionCode: String,
    var systemFrom: Boolean,
) {
    constructor() : this(0, null, "", "", "", "", false)
}