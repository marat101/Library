package ru.marat.library.di

import ru.marat.auth.di.AuthDependencies
import ru.marat.feature_home.di.HomeDependencies
import ru.marat.feature_profile.di.ProfileDependencies
import ru.marat.feature_root.di.RootDependencies
import ru.marat.feature_settings.di.SettingsDependencies

interface Dependencies :
    RootDependencies,
    HomeDependencies,
    SettingsDependencies,
    ProfileDependencies,
    AuthDependencies