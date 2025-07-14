package org.catsproject.project.ui.navigation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.catsproject.project.ui.view.CardCatalog
import org.catsproject.project.ui.viewmodel.FavoriteViewModel
import org.koin.compose.koinInject

@Composable
fun FavoriteScreen(
    vm: FavoriteViewModel = koinInject<FavoriteViewModel>(),
    navigate: (String) -> Unit
) {
    val listCat by vm.catsList.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }

    Column(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ){
            itemsIndexed(listCat){ _, item ->
                Box(
                    Modifier.fillMaxHeight()
                        .aspectRatio(0.7f)
                        .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navigate(item.id)
                    },
                    contentAlignment = Alignment.Center
                ) {
                    CardCatalog(item = item){
                        vm.removeCatById(item.id)
                    }
                }
            }
        }
    }
}
