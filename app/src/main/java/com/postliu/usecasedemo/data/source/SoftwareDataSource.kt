package com.postliu.usecasedemo.data.source

import com.postliu.usecasedemo.data.Result
import com.postliu.usecasedemo.data.Software

interface SoftwareDataSource {

    suspend fun saveSoftwares(software: List<Software>)

    suspend fun getSoftwares(filterSystem: Boolean = false): Result<List<Software>>

    suspend fun getSoftware(id: String): Result<Software?>

    suspend fun getSoftwareByName(
        labelName: String,
        filterSystem: Boolean = false
    ): Result<List<Software>>

    suspend fun clearSoftwares()
}