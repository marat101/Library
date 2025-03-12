package ru.marat.library

import android.app.Application
import ru.marat.core_di.InjectUtils
import ru.marat.library.di.DaggerAppComponent

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        InjectUtils.provideAppComponent(DaggerAppComponent.factory().create(this))
    }
}