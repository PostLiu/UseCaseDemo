package com.postliu.usecasedemo.software

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.postliu.usecasedemo.base.BaseBindingActivity
import com.postliu.usecasedemo.data.Result
import com.postliu.usecasedemo.databinding.ActivitySoftwareBinding
import com.postliu.usecasedemo.util.launchByPackageName
import com.postliu.usecasedemo.util.showSnackbar
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
            root.updateLayoutParams<FrameLayout.LayoutParams> {
                bottomMargin = 12
            }
            recyclerview.adapter = softwareAdapter.apply {
                setOnItemClickListener { data, _ ->
                    launchByPackageName(data.packageName).onFailure {
                        root.showSnackbar(it.stackTraceToString())
                    }
                }
            }
            inputName.addTextChangedListener {
                val name = it?.toString().orEmpty()
                viewModel.dispatch(SoftwareAction.FuzzySearch(name, true))
            }
            refresh.setOnRefreshListener {
                viewModel.dispatch(SoftwareAction.Refresh)
            }
            ViewCompat.setOnApplyWindowInsetsListener(
                inputName
            ) { _, insets ->
                val isVisibility = insets.isVisible(WindowInsetsCompat.Type.ime())
                if (!isVisibility) {
                    inputName.clearFocus()
                }
                insets
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.dispatch(SoftwareAction.Fetch)
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
}