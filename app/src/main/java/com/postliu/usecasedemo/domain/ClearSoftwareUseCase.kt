package com.postliu.usecasedemo.domain

import com.postliu.usecasedemo.data.source.SoftwareRepository
import javax.inject.Inject

class ClearSoftwareUseCase @Inject constructor(
    private val repository: SoftwareRepository
) {
    suspend operator fun invoke() {
        return repository.clearSoftwares()
    }
}