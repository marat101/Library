package ru.marat.feature_reader.di

import dagger.Component
import ru.marat.feature_reader.di.modules.ReaderModule
import ru.marat.feature_reader.di.modules.UseCasesModule
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ReaderScope

@ReaderScope
@Component(
    dependencies = [ReaderDependencies::class],
    modules = [ReaderModule::class, UseCasesModule::class]
)
interface ReaderComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: ReaderDependencies
        ): ReaderComponent
    }

    fun inject(target: ReaderContainer)
}