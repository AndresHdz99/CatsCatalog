package org.catsproject.project.ui.navigation.screen


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.catsproject.project.core.di.isDesktop
import org.catsproject.project.ui.view.CardCatalog
import org.catsproject.project.ui.view.PullToRefreshContainer
import org.catsproject.project.ui.viewmodel.CatsSearchViewModel
import org.koin.compose.koinInject

@Composable
fun PullRefreshView(
    vm: CatsSearchViewModel = koinInject<CatsSearchViewModel>(),
    navigate: (String) -> Unit
) {

    val selectedCatId by vm.selectedCatId.collectAsState()
    val listCat by vm.catsList.collectAsStateWithLifecycle()
    val isLoading by vm.isLoading.collectAsStateWithLifecycle()
    val refreshing by vm.isRefreshing.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }



    if (listCat.isEmpty()){
        Box(Modifier.fillMaxSize(),contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }

    }else{
        PullToRefreshContainer(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = refreshing,
            onRefresh = { vm.refresh() }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Center
            ) {
                itemsIndexed(listCat, key = { _, key -> key.id }) { index, item ->

                    val isSelected = item.id == selectedCatId

                    Box(
                        Modifier.padding(10.dp)
                            .then(if (isDesktop())
                                Modifier.aspectRatio(1f)
                                    .border(
                                        width = if (isSelected) 2.dp else 0.dp,
                                        color = if (isSelected) Color.Gray else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            else Modifier)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                navigate(item.id)
                                vm.selectCat(item)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        CardCatalog(item = item) {
                            vm.stateFavorite(item.id, !item.favorite)
                        }
                    }


                    if (index >= listCat.size - 2 && !isLoading) {
                        LaunchedEffect(listCat.size) {
                            vm.loadMoreCats()
                        }
                    }
                }

                // Loading indicator
                if (isLoading && listCat.isNotEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }


            }
        }
    }



}
