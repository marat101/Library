package ru.marat.feature_root.di

import dagger.Component
import ru.marat.feature_root.MainActivity
import ru.marat.feature_root.NavigationApiProvider

@Component(
    dependencies = [RootDependencies::class],
)
interface RootComponent {

    fun inject(target: MainActivity)
}