package io.atomic.sdk

import android.app.Application
import com.atomic.actioncards.sdk.AACSDK
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/** Entry point to our application. The SDK needs to do some init code [onCreate] */
class BoilerPlateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BoilerPlateApplication)
            modules(appModule)
        }
        AACSDK.init(this)
    }
}
