package ru.marat.feature_reader.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.marat.feature_reader.ReaderNavigationApi
import ru.marat.feature_reader.ReaderScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


class ReaderNavigationApiImpl: ReaderNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable<ReaderScreen>() {
            val uri = it.toRoute<ReaderScreen>().run {
                URLDecoder.decode(uri, StandardCharsets.UTF_8.toString()).toUri()
            }
            ReaderScreenUi(
                uri = uri,

            )
        }
    }
}