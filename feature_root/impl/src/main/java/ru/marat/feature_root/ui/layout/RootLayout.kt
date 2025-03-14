package ru.marat.feature_root.ui.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeLayout

@Composable
fun RootLayout( //todo придумать другую реализацию для нормального отображения нижней панели навигации
    root: @Composable (Int) -> Unit,
    bottomNavigationBar: @Composable () -> Unit
) {
    SubcomposeLayout { constraints ->

        val bottomBarPlaceable = subcompose(
            slotId = BOTTOM,
            content = bottomNavigationBar
        ).first().measure(
            constraints.copy(
                minHeight = 0
            )
        )
        val rootPlaceable = subcompose(
            slotId = ROOT,
            content = { root(bottomBarPlaceable.height) }
        ).map { it.measure(constraints) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            bottomBarPlaceable.place(
                x = 0,
                y = constraints.maxHeight - bottomBarPlaceable.height
            )
            rootPlaceable.forEach {
                it.placeWithLayer(0, 0)
            }
        }
    }
}

private const val ROOT = "root"
private const val BOTTOM = "bottom"