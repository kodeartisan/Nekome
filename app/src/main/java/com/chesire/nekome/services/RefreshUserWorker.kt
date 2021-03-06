package com.chesire.nekome.services

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.chesire.nekome.account.UserRepository
import com.chesire.nekome.server.Resource
import timber.log.Timber

/**
 * Worker object that handles updating the information for a user.
 *
 * When scheduled to run it will send a request to the [userRepo] to try to refresh the current
 * user data.
 */
class RefreshUserWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val userRepo: UserRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Timber.i("doWork RefreshUserWorker")

        if (userRepo.retrieveUserId() == null) {
            Timber.i("doWork no userId found, so cancelling")
            return Result.success()
        }

        Timber.i("doWork userId found, beginning to refresh")
        return if (userRepo.refreshUser() is Resource.Error) {
            Result.retry()
        } else {
            Result.success()
        }
    }
}
