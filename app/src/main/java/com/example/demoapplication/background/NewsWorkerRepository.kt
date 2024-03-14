package com.example.demoapplication.background

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit

class NewsWorkerRepository {
    private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED)
        .build()
    val periodicWork = PeriodicWorkRequest.Builder(
        NewsWorker::class.java,
        30L,
        TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .setInitialDelay(20L,TimeUnit.SECONDS)
        .build()
}