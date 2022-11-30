package com.postliu.usecasedemo.data

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "software")
data class Software(
    @PrimaryKey
    var uid: String = UUID.randomUUID().toString(),
    @Ignore
    var icon: Drawable? = null,
    var labelName: String,
    var packageName: String,
    var versionName: String,
    var versionCode: String,
    var systemFrom: Boolean,
) {
    constructor() : this("", null, "", "", "", "", false)
}