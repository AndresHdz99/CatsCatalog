package org.catsproject.project.ui.navigation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.catsproject.project.core.di.isDesktop
import org.catsproject.project.ui.navigation.NavigationEnum
import org.catsproject.project.ui.view.CardCatalog
import org.catsproject.project.ui.viewmodel.FavoriteViewModel
import org.koin.compose.koinInject

@Composable
fun FavoriteScreenDesktop(
    vm: FavoriteViewModel = koinInject<FavoriteViewModel>()
){

    val listCat by vm.catsList.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }
    var idCat by remember { mutableStateOf("") }
    var favoriteVisibility by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Row {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth().weight(1f),
            columns = GridCells.Fixed(if (favoriteVisibility) 2 else 3),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ){
            itemsIndexed(listCat){ _, item ->
                Box(
                    Modifier.fillMaxHeight()
                        .then(if (isDesktop())
                            Modifier.aspectRatio(1f)

                        else Modifier)
                        .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        idCat = item.id
                        favoriteVisibility = true
                        //navigate(item.id)
                    },
                    contentAlignment = Alignment.Center
                ) {
                    CardCatalog(item = item){
                        vm.removeCatById(item.id)
                        if (item.id == idCat){
                            favoriteVisibility = false
                            scope.launch {
                                delay(500)
                                idCat = ""
                            }
                        }
                    }
                }
            }
        }


        AnimatedVisibility(favoriteVisibility){
            Box(Modifier.fillMaxWidth(0.5f)){
                InformationCart(idCat, NavigationEnum.FAVORITE)
            }
        }




    }
}