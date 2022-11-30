package com.postliu.usecasedemo.software

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.postliu.usecasedemo.data.Result
import com.postliu.usecasedemo.data.Software
import com.postliu.usecasedemo.domain.ClearSoftwareUseCase
import com.postliu.usecasedemo.domain.GetSoftwaresUseCase
import com.postliu.usecasedemo.domain.SaveSoftwareUseCase
import com.postliu.usecasedemo.util.SoftwareUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SoftwareAction {
    object Fetch : SoftwareAction()
    object Refresh : SoftwareAction()
    data class FuzzySearch(
        val labelName: String,
        val filterSystem: Boolean = true
    ) : SoftwareAction()
}

@HiltViewModel
class SoftwareViewModel @Inject constructor(
    private val saveSoftwareUseCase: SaveSoftwareUseCase,
    private val getSoftwaresUseCase: GetSoftwaresUseCase,
    private val clearSoftwareUseCase: ClearSoftwareUseCase,
    application: Application,
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "SoftwareViewModel"
    }

    private val mSoftwares = MutableStateFlow<Result<List<Software>>>(Result.Loading)
    val softwares = mSoftwares.asStateFlow()

    fun dispatch(action: SoftwareAction) = when (action) {
        is SoftwareAction.Fetch -> allSoftware()
        is SoftwareAction.Refresh -> allSoftware()
        is SoftwareAction.FuzzySearch -> fuzzySearch(action.labelName)
    }

    private fun allSoftware(filterSystem: Boolean = true) = viewModelScope.launch {
        flow {
            clearSoftwareUseCase()
            val allSoftware = SoftwareUtils.getInstalledPackages(getApplication())
            Log.e(TAG, "allSoftware: $allSoftware")
            saveSoftwareUseCase(allSoftware)
            emit(getSoftwaresUseCase(filterSystem))
        }.catch {
            mSoftwares.emit(Result.Error(it))
        }.collectLatest {
            mSoftwares.emit(it)
        }
    }

    @OptIn(FlowPreview::class)
    private fun fuzzySearch(labelName: String,filterSystem: Boolean = true) = viewModelScope.launch {
        flow {
            emit(getSoftwaresUseCase(labelName,filterSystem))
        }.debounce(500).catch {
            mSoftwares.emit(Result.Error(it))
        }.collectLatest {
            mSoftwares.emit(it)
        }
    }
}