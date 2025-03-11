package ru.marat.core_di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

//    @Component.Builder
//    interface Builder {
//        fun dependencies(@BindsInstance context: Context) : Builder
//        fun build(): AppComponent
//    }

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }
}