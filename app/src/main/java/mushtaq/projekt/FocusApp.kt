package mushtaq.projekt

import android.app.Application
import mushtaq.projekt.data.local.preferences.AppPreferenceManager

class FocusApp : Application() {

    lateinit var preferenceManager: AppPreferenceManager

    override fun onCreate(){
        super.onCreate()
        preferenceManager = AppPreferenceManager(this)
    }
}