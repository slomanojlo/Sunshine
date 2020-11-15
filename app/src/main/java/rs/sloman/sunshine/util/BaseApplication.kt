package rs.sloman.sunshine.util

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import rs.sloman.sunshine.BuildConfig
import timber.log.Timber

@HiltAndroidApp
/**BaseApplication of the app. In charge of instantiating Dagger-Hilt and Timber.*/
class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}