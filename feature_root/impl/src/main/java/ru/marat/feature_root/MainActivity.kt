package ru.marat.feature_root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.json.Json
import ru.marat.core_di.InjectUtils
import ru.marat.feature_home.HomeScreen
import ru.marat.feature_profile.ProfileScreen
import ru.marat.feature_root.di.DaggerRootComponent
import ru.marat.library.ui.theme.LibraryTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationApiProvider: NavigationApiProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerRootComponent
            .builder()
            .rootDependencies(InjectUtils.appDependencies())
            .build()
            .inject(this)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LibraryTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Navigation(
                        modifier = Modifier.weight(1f),
                        navController = navController,
                        startDestination = HomeScreen,
                        provider = navigationApiProvider
                    )
                    Row(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val buttons = remember { listOf(HomeScreen,ProfileScreen) }
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        buttons.forEach {
                            val isSelected = navBackStackEntry?.destination?.route == it::class.qualifiedName
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .then(
                                        if (isSelected)
                                            Modifier.background(Color.DarkGray, CircleShape)
                                        else Modifier
                                    )
                                    .clickable {
                                        navController.navigate(it)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = it.toString(),
                                    color = if (isSelected) Color.White else Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LibraryTheme {
        Greeting("Android")
    }
}