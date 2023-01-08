package app.vahid.appinitializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}