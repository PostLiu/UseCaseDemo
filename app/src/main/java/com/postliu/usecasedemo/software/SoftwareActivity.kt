package com.postliu.usecasedemo.software

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.postliu.usecasedemo.base.BaseBindingActivity
import com.postliu.usecasedemo.data.Result
import com.postliu.usecasedemo.databinding.ActivitySoftwareBinding
import com.postliu.usecasedemo.dialog.UnInstallSoftwareDialog
import com.postliu.usecasedemo.util.SoftwareUtils
import com.postliu.usecasedemo.util.launchByPackageName
import com.postliu.usecasedemo.util.showSnackbar
import com.postliu.usecasedemo.util.uninstallSoftware
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SoftwareActivity : BaseBindingActivity<ActivitySoftwareBinding>() {

    companion object {
        private const val TAG = "SoftwareActivity"
    }

    private val viewModel: SoftwareViewModel by viewModels()

    @Inject
    lateinit var softwareAdapter: SoftwareAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            recyclerview.adapter = softwareAdapter.apply {
                setOnItemClickListener { data, _ ->
                    launchByPackageName(data.packageName).onFailure {
                        Log.e(TAG, "onCreate: $it")
                        root.showSnackbar(it.stackTraceToString())
                    }
                }
                setOnItemLongClickListener { data, _ ->
                    val icon = SoftwareUtils.getPackageIcon(this@SoftwareActivity, data.packageName)
                    UnInstallSoftwareDialog.build(this@SoftwareActivity)
                        .setLogo(icon)
                        .setMessage("是否允许卸载【${data.labelName}】")
                        .setCancelClickListener { dismiss() }
                        .setSureClickListener {
                            uninstallSoftware(data.packageName)
                                .onFailure { root.showSnackbar(it.stackTraceToString()) }
                                .onSuccess { dismiss() }
                        }.show()
                    return@setOnItemLongClickListener true
                }
            }
            inputName.addTextChangedListener {
                val name = it?.toString().orEmpty()
                viewModel.dispatch(SoftwareAction.FuzzySearch(name, false))
            }
            refresh.setOnRefreshListener {
                if (inputName.text.isNullOrEmpty()) {
                    viewModel.dispatch(SoftwareAction.Refresh)
                } else {
                    val name = inputName.text.toString()
                    viewModel.dispatch(SoftwareAction.FuzzySearch(name, false))
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dispatch(SoftwareAction.Fetch)
            }
        }
    }

    override fun CoroutineScope.uiReceived() {
        launch {
            viewModel.softwares.collectLatest {
                Log.e(TAG, "uiReceived: $it")
                when (it) {
                    is Result.Loading -> {
                        binding.refresh.isRefreshing = true
                    }

                    is Result.Error -> {
                        binding.refresh.isRefreshing = false
                    }

                    is Result.Success -> {
                        binding.refresh.isRefreshing = false
                        Log.e(TAG, "uiReceived: size is ${it.data.size}")
                        softwareAdapter.submitList(it.data)
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.dispatch(SoftwareAction.Clear)
    }
}