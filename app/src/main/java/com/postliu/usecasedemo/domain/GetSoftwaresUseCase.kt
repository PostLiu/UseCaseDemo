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

    /**
     * 条件筛选查询
     *
     * @param labelName 搜索的名称
     * @param filterSystem 是否过滤系统应用
     * @return
     */
    suspend operator fun invoke(
        labelName: String,
        filterSystem: Boolean = false
    ): Result<List<Software>> {
        return if (labelName.isEmpty()) {
            repository.getSoftwares(filterSystem)
        } else {
            repository.getSoftwareByName(labelName, filterSystem)
        }
    }
}