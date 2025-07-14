package org.catsproject.project.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import catscatalog.composeapp.generated.resources.Res
import catscatalog.composeapp.generated.resources.ic_dehaze
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopBar(onClick:()->Unit){
    Box(Modifier.fillMaxWidth().padding(15.dp)){
        Icon(
            painter = painterResource(Res.drawable.ic_dehaze),
            contentDescription = null, tint = Color.Black,
            modifier = Modifier.size(34.dp).clickable {
                onClick()
            }
        )
    }
}