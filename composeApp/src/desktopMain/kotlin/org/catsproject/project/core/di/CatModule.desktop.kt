package org.catsproject.project.core.di

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual fun provideSettings(): Settings {
    return PreferencesSettings(
        Preferences.userNodeForPackage(Settings::class.java)
    )
}

actual fun isDesktop():  Boolean = true