package ru.marat.feature_book.di

import dagger.BindsInstance
import dagger.Component
import ru.marat.feature_book.di.modules.BookModule
import ru.marat.feature_book.di.modules.BookViewModelsModule
import ru.marat.feature_book.di.modules.UseCasesModule
import javax.inject.Named
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BookScope

@BookScope
@Component(
    dependencies = [BookDependencies::class],
    modules = [
        BookModule::class,
        BookViewModelsModule::class,
        UseCasesModule::class
    ]
)
interface BookComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("bookId") bookId: Long,
            dependencies: BookDependencies
        ): BookComponent
    }

    fun inject(target: BookContainer)
}