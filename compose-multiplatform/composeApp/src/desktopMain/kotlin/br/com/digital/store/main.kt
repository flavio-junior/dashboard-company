package br.com.digital.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.compose.rememberNavController
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.logo
import br.com.digital.store.di.desktopModule
import br.com.digital.store.features.account.di.accountModule
import br.com.digital.store.features.category.di.categoryModule
import br.com.digital.store.features.networking.di.networkModule
import br.com.digital.store.strings.StringsUtils.MAIN_NAME
import br.com.digital.store.theme.Themes
import br.com.digital.store.navigation.Navigation
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(
            modules = listOf(
                accountModule,
                categoryModule,
                desktopModule,
                networkModule
            )
        )
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = MAIN_NAME,
            icon = painterResource(resource = Res.drawable.logo),
            state = rememberWindowState(placement = WindowPlacement.Maximized)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .fillMaxSize()
            ) {
                Navigation(navController = rememberNavController())
            }
        }
    }
}