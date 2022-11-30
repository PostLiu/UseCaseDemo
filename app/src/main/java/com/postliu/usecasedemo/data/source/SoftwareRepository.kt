package com.postliu.usecasedemo.data.source

import com.postliu.usecasedemo.data.Result
import com.postliu.usecasedemo.data.Software

interface SoftwareRepository {

    /**
     * 保存所有应用
     *
     * @param software
     */
    suspend fun saveSoftwares(software: List<Software>)

    /**
     * 获取所有应用
     *
     * @return
     */
    suspend fun getSoftwares(filterSystem: Boolean = false): Result<List<Software>>

    /**
     * 获取单个应用
     *
     * @param id 应用id
     * @return
     */
    suspend fun getSoftware(id: String): Result<Software?>

    /**
     * 根据名称模糊查询应用
     *
     * @param labelName
     * @return
     */
    suspend fun getSoftwareByName(
        labelName: String,
        filterSystem: Boolean = false
    ): Result<List<Software>>

    /**
     * 清空所有应用
     *
     */
    suspend fun clearSoftwares()
}