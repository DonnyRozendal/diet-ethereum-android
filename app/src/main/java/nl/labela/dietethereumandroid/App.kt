package nl.labela.dietethereumandroid

import android.app.Application
import nl.labela.dietethereumandroid.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModules)
            androidContext(applicationContext)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}