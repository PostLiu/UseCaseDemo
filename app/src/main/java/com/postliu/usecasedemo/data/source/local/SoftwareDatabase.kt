package com.postliu.usecasedemo.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.postliu.usecasedemo.data.Software

@Database(entities = [Software::class], version = 1, exportSchema = false)
abstract class SoftwareDatabase : RoomDatabase() {

    abstract fun softwareDao(): SoftwareDao
}