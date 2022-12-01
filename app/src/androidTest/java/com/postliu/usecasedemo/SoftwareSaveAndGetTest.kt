package com.postliu.usecasedemo

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.postliu.usecasedemo.data.source.DefaultSoftwareRepository
import com.postliu.usecasedemo.data.source.SoftwareDataSource
import com.postliu.usecasedemo.data.source.SoftwareRepository
import com.postliu.usecasedemo.data.source.local.SoftwareDao
import com.postliu.usecasedemo.data.source.local.SoftwareDatabase
import com.postliu.usecasedemo.data.source.local.SoftwareLocalDataSource
import com.postliu.usecasedemo.domain.GetSoftwaresUseCase
import com.postliu.usecasedemo.domain.SaveSoftwareUseCase
import com.postliu.usecasedemo.util.SoftwareUtils
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SoftwareSaveAndGetTest {

    private lateinit var database: SoftwareDatabase
    private lateinit var dao: SoftwareDao
    private lateinit var dataSource: SoftwareDataSource
    private lateinit var repository: SoftwareRepository
    private lateinit var saveSoftwareUseCase: SaveSoftwareUseCase
    private lateinit var getSoftwaresUseCase: GetSoftwaresUseCase

    @Test
    fun saveSoftwares() = runBlocking {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val softwares = SoftwareUtils.getInstalledPackages(appContext)
        saveSoftwareUseCase(softwares)
        val getSoftwares = getSoftwaresUseCase("米", false)
        println("读取保存的软件：$getSoftwares")
    }

    /**
     * 运行测试用例之前初始化
     *
     */
    @Before
    fun createDatabase() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database =
            Room.databaseBuilder(appContext, SoftwareDatabase::class.java, "software.db").build()
        dao = database.softwareDao()
        dataSource = SoftwareLocalDataSource(dao)
        repository = DefaultSoftwareRepository(dataSource)
        saveSoftwareUseCase = SaveSoftwareUseCase(repository)
        getSoftwaresUseCase = GetSoftwaresUseCase(repository)
    }

    /**
     * 测试用例运行完成后的操作
     *
     */
    @After
    fun closeDatabase() {
        database.close()
    }
}