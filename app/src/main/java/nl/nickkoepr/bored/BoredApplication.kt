package nl.nickkoepr.bored

import android.app.Application
import nl.nickkoepr.bored.data.AppContainer
import nl.nickkoepr.bored.data.BoredAppContainer

class BoredApplication : Application() {

    lateinit var appContainer: AppContainer
        private set
    override fun onCreate() {
        super.onCreate()
        appContainer = BoredAppContainer(this)
    }
}