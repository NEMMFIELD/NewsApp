package com.example.demoapplication.background

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.demoapplication.data.model.ArticlesItem
import com.example.demoapplication.domain.NewsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class NewsWorker @AssistedInject constructor (
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    val repository: NewsRepository
) : CoroutineWorker(context, workerParameters) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            repository.getTopHeadlines(1)?.articles?.first()?.let { showNotification(it) }
            Log.d("NewsWorker","It works!")
            Result.success()
        }.getOrElse {
            Result.failure()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showNotification(currentNews: ArticlesItem) {
        val notification = NewsNotification(context = context)
        notification.createNotificationChannel()
        notification.showNotification(currentNews)
    }
}