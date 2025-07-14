package org.catsproject.project.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
object LoginScreen

@Serializable
object CatalogScreen

@Serializable
data class InformationScreen(
    val id:String,
    val navigation: NavigationEnum
)

@Serializable
object FavoriteScreen

@Serializable
object DesktopHomeScreen

@Serializable
object FavoriteScreenDesktop

