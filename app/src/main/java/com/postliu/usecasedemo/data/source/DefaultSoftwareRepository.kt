package com.postliu.usecasedemo.data.source

import com.postliu.usecasedemo.data.Result
import com.postliu.usecasedemo.data.Software
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class DefaultSoftwareRepository(
    private val dataSource: SoftwareDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SoftwareRepository {

    override suspend fun saveSoftwares(software: List<Software>) {
        withContext(ioDispatcher) {
            coroutineScope {
                dataSource.saveSoftwares(software)
            }
        }
    }

    override suspend fun getSoftwares(filterSystem: Boolean): Result<List<Software>> {
        return withContext(ioDispatcher) {
            dataSource.getSoftwares()
        }
    }

    override suspend fun getSoftware(id: String): Result<Software?> {
        return withContext(ioDispatcher) {
            dataSource.getSoftware(id)
        }
    }

    override suspend fun getSoftwareByName(
        labelName: String,
        filterSystem: Boolean
    ): Result<List<Software>> {
        return withContext(ioDispatcher) {
            dataSource.getSoftwareByName(labelName, filterSystem)
        }
    }

    override suspend fun clearSoftwares() {
        withContext(ioDispatcher) {
            dataSource.clearSoftwares()
        }
    }
}