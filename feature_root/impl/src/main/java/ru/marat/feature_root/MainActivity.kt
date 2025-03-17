package ru.marat.feature_root

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.marat.core_di.InjectUtils
import ru.marat.feature_home.HomeScreen
import ru.marat.feature_profile.ProfileScreen
import ru.marat.feature_root.di.RootDependencies
import ru.marat.feature_root.ui.bottom_navigation.BottomNavigationBar
import ru.marat.feature_root.ui.bottom_navigation.NavigationButton
import ru.marat.feature_root.ui.layout.RootLayout
import ru.marat.library.ui.theme.LibraryTheme
import ru.marat.navigation_api.AppNavController
import ru.marat.navigation_api.Screen
import ru.marat.navigation_api.register
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationApiProvider: NavigationApiProvider

    @Inject
    lateinit var appNavigation: AppNavController

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        val apis = navigationApiProvider.getAll()
        val appNavigation = appNavigation as AppNavigationController
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            appNavigation.init(navController)
            LibraryTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    val bottomBarState = remember { mutableStateOf(NavigationButton.MAIN) }
                    RootLayout(
                        root = {
                            val density = LocalDensity.current
                            NavHost(
                                modifier = Modifier.fillMaxSize(),
                                navController = navController,
                                startDestination = HomeScreen
                            ) {
                                apis.forEach { api ->
                                    register(
                                        featureApi = api,
                                        navController = navController,
                                        modifier = Modifier.padding(bottom = density.run { it.toDp() })
                                    )
                                }
                            }
                        }
                    ) {
                        BottomNavigationBar(
                            modifier = Modifier,
                            selectedButton = bottomBarState.value,
                            buttons = NavigationButton.entries,
                            onClick = {
                                if (!listOf(
                                        HomeScreen::class.qualifiedName,
                                        ProfileScreen::class.qualifiedName
                                    ).contains(navController.currentBackStackEntry?.destination?.route)
                                ) return@BottomNavigationBar
                                bottomBarState.value = it
                                navController.navigate(it.route) {
                                    launchSingleTop = true
                                    popUpTo(navController.graph.id) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun inject() =
        InjectUtils.appDependencies<RootDependencies>().component.create().inject(this)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LibraryTheme {

    }
}