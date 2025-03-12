package ru.marat.library.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.marat.core_di.BaseComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent: BaseComponent, Dependencies {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }
}