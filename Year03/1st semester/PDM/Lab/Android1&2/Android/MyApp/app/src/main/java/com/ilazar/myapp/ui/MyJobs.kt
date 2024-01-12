package com.ilazar.myservices.ui

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.*
import com.ilazar.myapp.core.TAG
import com.ilazar.myservices.util.MyWorker
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

data class MyJobUiState(val isRunning: Boolean = false, val secondsInApp: Int = 0)

class MyJobsViewModel(application: Application) : AndroidViewModel(application) {
    var uiState by mutableStateOf(MyJobUiState())
        private set
    private var workManager: WorkManager
    private var workId: UUID? = null

    init {
        workManager =  WorkManager.getInstance(getApplication())
        startJob()
    }

    private fun startJob() {
        viewModelScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val myPeriodicWork = PeriodicWorkRequestBuilder<MyWorker>(20, TimeUnit.SECONDS)
            val myWork = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setConstraints(constraints)
                .build()
            workId = myWork.id
            uiState = uiState.copy(isRunning = true)
            workManager.apply {
                // enqueue Work
                enqueue(myWork)
                // observe work status
                getWorkInfoByIdLiveData(workId!!).asFlow().collect {
                    Log.d("MyJobsViewModel", "$it")
                    uiState = uiState.copy(
                        isRunning = !it.state.isFinished,
                        secondsInApp = it.progress.getInt("progress", 0),
                    )

                    if (it.state.isFinished) {
                        uiState = uiState.copy(
                        )
                    }
                }
            }
        }
    }

    fun cancelJob() {
        workManager.cancelWorkById(workId!!)
    }

    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MyJobsViewModel(application)
            }
        }
    }
}

@Composable
fun MyJobs() {
    val myJobsViewModel = viewModel<MyJobsViewModel>(
        factory = MyJobsViewModel.Factory(
            LocalContext.current.applicationContext as Application
        )
    )

    Column {
        Text(
            "${myJobsViewModel.uiState}",
            style = MaterialTheme.typography.h4,
        )
        Button(onClick = { myJobsViewModel.cancelJob() }) {
            Text("Cancel")
        }
    }
}
