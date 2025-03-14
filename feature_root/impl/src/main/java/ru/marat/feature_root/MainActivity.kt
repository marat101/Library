package ru.marat.feature_root

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import ru.marat.core_di.InjectUtils
import ru.marat.feature_home.HomeScreen
import ru.marat.feature_root.di.DaggerRootComponent
import ru.marat.feature_root.ui.bottom_navigation.BottomNavigationBar
import ru.marat.feature_root.ui.bottom_navigation.NavigationButton
import ru.marat.feature_root.ui.layout.RootLayout
import ru.marat.library.ui.theme.LibraryTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationApiProvider: NavigationApiProvider

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerRootComponent
            .builder()
            .rootDependencies(InjectUtils.appDependencies())
            .build()
            .inject(this)

        val apis = navigationApiProvider.getAll()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LibraryTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val bottomBarState = remember { mutableStateOf(NavigationButton.MAIN) }
                    RootLayout(
                        root = {
                            val density = LocalDensity.current
                            RootScreen(
                                modifier = Modifier.padding(bottom = density.run { it.toDp() }),
                                navController = navController,
                                startDestination = HomeScreen,
                                apis = apis
                            )
                        }
                    ) {
                        BottomNavigationBar(
                            selectedButton = bottomBarState.value,
                            buttons = NavigationButton.entries,
                            onClick = {
                                if (it == bottomBarState.value) return@BottomNavigationBar
                                bottomBarState.value = it
                                navController.navigate(it.route) //todo
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LibraryTheme {

    }
}