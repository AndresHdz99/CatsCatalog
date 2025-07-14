package org.catsproject.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import catscatalog.composeapp.generated.resources.Res
import catscatalog.composeapp.generated.resources.compose_multiplatform
import org.catsproject.project.core.di.isDesktop
import org.catsproject.project.ui.navigation.NavigateDesktopWrapper
import org.catsproject.project.ui.navigation.NavigationWrapper
import org.catsproject.project.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        if (isDesktop()){
            NavigateDesktopWrapper()
        }else{
            NavigationWrapper()
        }
    }
}