package org.android.go.sopt.util

import android.app.Application
import androidx.databinding.ktx.BuildConfig
import timber.log.Timber

class TimberApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}