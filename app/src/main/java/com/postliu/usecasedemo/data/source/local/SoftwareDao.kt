package com.postliu.usecasedemo.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.postliu.usecasedemo.data.Software

@Dao
interface SoftwareDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSoftwares(software: List<Software>)

    @Query("SELECT * FROM software WHERE systemFrom=:filterSystem")
    suspend fun getSoftwares(filterSystem: Boolean = false): List<Software>

    @Query("SELECT * FROM software WHERE uid=:id")
    suspend fun getSoftwareById(id: String): Software?

    @Query("SELECT * FROM software WHERE systemFrom=:filterSystem AND labelName LIKE '%' || :labelName || '%' ")
    suspend fun getSoftwareByName(labelName: String, filterSystem: Boolean = false): List<Software>

    @Query("DELETE FROM software")
    suspend fun clearSoftwares()
}