package com.postliu.usecasedemo.domain

import com.postliu.usecasedemo.data.Software
import com.postliu.usecasedemo.data.source.SoftwareRepository
import javax.inject.Inject

class SaveSoftwareUseCase @Inject constructor(
    private val repository: SoftwareRepository
) {
    suspend operator fun invoke(software: List<Software>) {
        return repository.saveSoftwares(software)
    }
}