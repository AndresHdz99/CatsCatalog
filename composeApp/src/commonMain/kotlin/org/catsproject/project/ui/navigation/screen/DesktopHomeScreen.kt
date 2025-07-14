package org.catsproject.project.ui.navigation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.catsproject.project.ui.navigation.NavigationEnum

@Composable
fun DesktopHomeScreen(){

    var idCat by rememberSaveable { mutableStateOf("") }



    Row(Modifier.fillMaxSize()){

        Box(Modifier.fillMaxWidth().weight(1f)){
            PullRefreshView{ id->
                idCat = id
            }
        }

        Box(Modifier.fillMaxWidth().weight(1f)){
            InformationCart(idCat,NavigationEnum.CATALOG)
        }


    }
}