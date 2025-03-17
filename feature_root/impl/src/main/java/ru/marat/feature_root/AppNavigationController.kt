package ru.marat.feature_root

import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.modules.SerializersModule
import ru.marat.navigation_api.AppNavController
import ru.marat.navigation_api.Screen

@Stable
@SuppressLint("RestrictedApi")
@MainThread
class AppNavigationController : AppNavController {

    private var navController: NavController? = null

    fun init(navController: NavController) {

        this.navController = navController
    }

    override fun navigate(route: Screen, options: (NavOptionsBuilder.() -> Unit)?) {
        checkNavController()
        if (options != null) navController?.navigate(route, options)
        else navController?.navigate(route)
    }

    override fun popBackStack() {
        checkNavController()
        navController?.popBackStack()
    }

    private fun checkNavController() {
        if (navController == null) throw IllegalStateException("Jetpack compose NavController is not provided")
    }

}