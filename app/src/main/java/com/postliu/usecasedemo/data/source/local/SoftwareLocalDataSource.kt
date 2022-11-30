package com.postliu.usecasedemo.data.source.local

import android.util.Log
import com.postliu.usecasedemo.data.Result
import com.postliu.usecasedemo.data.Software
import com.postliu.usecasedemo.data.source.SoftwareDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SoftwareLocalDataSource internal constructor(
    private val softwareDao: SoftwareDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SoftwareDataSource {

    override suspend fun saveSoftwares(software: List<Software>) = withContext(ioDispatcher) {
        softwareDao.saveSoftwares(software)
    }

    override suspend fun getSoftwares(filterSystem: Boolean): Result<List<Software>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(softwareDao.getSoftwares())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getSoftware(id: String): Result<Software?> = withContext(ioDispatcher) {
        kotlin.runCatching {
            Result.Success(softwareDao.getSoftwareById(id))
        }.getOrElse { Result.Error(it) }
    }

    override suspend fun getSoftwareByName(
        labelName: String,
        filterSystem: Boolean
    ): Result<List<Software>> =
        withContext(ioDispatcher) {
            kotlin.runCatching {
                Result.Success(
                    softwareDao.getSoftwareByName(labelName, filterSystem).also { softwares ->
                        Log.w("TAG", "getSoftwareByName: ${softwares.map { it.systemFrom }}")
                    })
            }.getOrElse { Result.Error(it) }
        }

    override suspend fun clearSoftwares() = withContext(ioDispatcher) {
        softwareDao.clearSoftwares()
    }
}