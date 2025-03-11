package ru.marat.library

import android.app.Application
import ru.marat.core_di.InjectUtils

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        InjectUtils.provideAppComponent(this)
    }
}