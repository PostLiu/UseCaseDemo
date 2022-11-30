package com.postliu.usecasedemo.domain

import com.postliu.usecasedemo.data.Result
import com.postliu.usecasedemo.data.Software
import com.postliu.usecasedemo.data.source.SoftwareRepository
import javax.inject.Inject

class GetSoftwaresUseCase @Inject constructor(
    private val repository: SoftwareRepository
) {
    suspend operator fun invoke(filterSystem: Boolean = false): Result<List<Software>> {
        return repository.getSoftwares(filterSystem)
    }

    suspend operator fun invoke(
        labelName: String,
        filterSystem: Boolean = false
    ): Result<List<Software>> {
        return repository.getSoftwareByName(labelName, filterSystem)
    }
}