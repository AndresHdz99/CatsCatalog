package org.catsproject.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.catsproject.project.core.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CatsCatalog",
        ) {
            App()
        }
    }
}
