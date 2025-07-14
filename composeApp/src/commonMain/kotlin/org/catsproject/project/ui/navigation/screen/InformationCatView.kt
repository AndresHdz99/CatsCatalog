package org.catsproject.project.ui.navigation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Delay
import org.catsproject.project.core.di.isDesktop
import org.catsproject.project.ui.navigation.NavigationEnum
import org.catsproject.project.ui.view.IconFavorite
import org.catsproject.project.ui.view.OptimizedAsyncImage
import org.catsproject.project.ui.viewmodel.CatInformationViewModel
import org.koin.compose.koinInject


@Composable
fun InformationCart(
    id:String,
    navigation: NavigationEnum,
    vm: CatInformationViewModel = koinInject<CatInformationViewModel>(),
    onBack:()->Unit = {}
){

    val description by vm.description.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()


    LaunchedEffect(id) {

        vm.getDescriptionCat(id)

    }

    Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(scrollState)) {

        Box(Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .clip(RoundedCornerShape(15.dp))
        ){

            OptimizedAsyncImage(
                description?.url ?: "",
                contentDescription = "name",
                modifier = Modifier
                    .heightIn(max = 400.dp)

            )


            if (!isDesktop()){
                IconFavorite(description?.favorite ?: false){
                    vm.stateFavorite(
                        idCat = id,
                        state = !(description?.favorite ?: false)
                    )

                    if(navigation == NavigationEnum.FAVORITE){
                        onBack()
                    }
                }
            }
        }

        if (description != null){
            Spacer(Modifier.height(10.dp))

            Text(description?.name ?: "", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))


            Text("Origen: ${description?.origin ?: ""}", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(5.dp))

            Text("Line Span: ${description?.life_span ?: ""} year", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(20.dp))

            ProgressIndicator("Energy Level",description?.energyLevel ?: 0f)


            Spacer(Modifier.height(10.dp))

            ProgressIndicator("Intelligence",description?.intelligence ?: 0f)

            Spacer(Modifier.height(10.dp))

            Text("${description?.information}", modifier = Modifier.fillMaxWidth(),fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }


    }

}

@Composable
fun ProgressIndicator(text:String,progres:Float){

    Row(Modifier.fillMaxWidth()) {

        Text(text, modifier = Modifier.fillMaxWidth().weight(1f))

        Box(
            modifier = Modifier
                .weight(2f)
                .height(15.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.LightGray)
        ) {
            LinearProgressIndicator(
                progress = { progres },
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.Transparent
            )
        }





    }


}