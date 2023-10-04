package io.atomic.android_compose_boilerplate

import android.app.Application
import com.atomic.actioncards.sdk.AACSDK


/** Entry point to our application. The SDK needs to do some init code [onCreate] */
class BoilerPlateApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        AACSDK.init(this)
    }

}