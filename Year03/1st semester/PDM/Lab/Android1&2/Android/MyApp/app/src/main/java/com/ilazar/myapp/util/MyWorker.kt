package com.ilazar.myservices.util

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit.SECONDS

class MyWorker(
    context: Context,
    val workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        // perform long running operation

        Log.d("WorkerView", "entered worker")
        var s = 0
        while (true) {
            SECONDS.sleep(1)
            setProgressAsync(workDataOf("progress" to s))
            Log.d("Worker", s.toString())
            s += 1
        }
        return Result.success(workDataOf("secondsInApp" to s))
    }
}