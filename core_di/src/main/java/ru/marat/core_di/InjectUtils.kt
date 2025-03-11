package ru.marat.core_di

import android.content.Context

object InjectUtils {

    private var _appComponent: AppComponent? = null


    val appComponent: AppComponent
        get() = _appComponent ?: throw IllegalStateException("AppComponent is not provided")

    fun provideAppComponent(appContext: Context) {
        if (_appComponent == null)
            _appComponent = DaggerAppComponent.factory().create(appContext)
        else throw IllegalStateException("AppComponent is already provided")
    }
}