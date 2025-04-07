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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.marat.core_di.InjectUtils
import ru.marat.feature_home.HomeScreen
import ru.marat.feature_profile.SearchScreen
import ru.marat.feature_root.di.RootDependencies
import ru.marat.feature_root.ui.RootViewModel
import ru.marat.feature_root.ui.bottom_navigation.BottomNavigationBar
import ru.marat.feature_root.ui.bottom_navigation.NavigationButton
import ru.marat.feature_root.ui.layout.RootLayout
import ru.marat.feature_settings.SettingsScreens
import ru.marat.library.ui.theme.LibraryTheme
import ru.marat.navigation_api.register
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationApiProvider: NavigationApiProvider

    lateinit var viewModel: RootViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        val apis = navigationApiProvider.getAll()
        enableEdgeToEdge()
        setContent {
            val state = viewModel.state.collectAsState()
            val navController = rememberNavController()
            viewModel.initNavController(navController)
            if (state.value.theme == null) return@setContent
            LibraryTheme(
                currentTheme = state.value.theme!!,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
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
                            selectedButton = state.value.activeButton,
                            buttons = NavigationButton.entries,
                            onClick = { //todo придумать что то получше
                                if (listOf(
                                        SettingsScreens.Feedback::class.qualifiedName,
                                        SearchScreen::class.qualifiedName,
                                    ).contains(navController.currentBackStackEntry?.destination?.route)
                                ) return@BottomNavigationBar
                                viewModel.bottomNavigation(navController.graph.id, it)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun inject() {
        val component = InjectUtils.appDependencies<RootDependencies>().rootComponent.create()
        component.inject(this)
        viewModel =
            ViewModelProvider(this, component.rootViewModelFactory)[RootViewModel::class.java]
    }
}