package org.catsproject.project.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import catscatalog.composeapp.generated.resources.Res
import catscatalog.composeapp.generated.resources.ic_favorite
import catscatalog.composeapp.generated.resources.ic_image_error
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import coil3.compose.AsyncImagePainter.State
import org.catsproject.project.data.model.CatsImage


@Composable
fun CardCatalog(item: CatsImage, click: () -> Unit){


    Column (Modifier.padding(15.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){



        Box(
            Modifier
                .size(200.dp),

            contentAlignment = Alignment.BottomEnd
        ){


            OptimizedAsyncImage(
                item.url,
                contentDescription = "name",
                modifier = Modifier.clip(RoundedCornerShape(50)).aspectRatio(1f)

            )

            IconFavorite(item.favorite){
                click()
            }

        }


        Spacer(Modifier.height(10.dp))

        Text(text = item.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(10.dp))

        Text(text = item.temperament,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

    }

}

@Composable
fun OptimizedAsyncImage(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    var currentState by remember { mutableStateOf<State?>(null) }



    Box(modifier = modifier) {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            onState = { state ->
                currentState = state
            }
        )

        when (currentState) {
            is State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_image_error),
                        contentDescription = "Error"
                    )
                }
            }
            else -> {

            }
        }
    }
}

@Composable
fun IconFavorite(state:Boolean,click:()->Unit){

    val interactionSource = remember { MutableInteractionSource() }

    Icon(
        modifier = Modifier
            .padding(10.dp)
            .size(35.dp)
            .clip(RoundedCornerShape(50))
            .background(Color.White)
            .padding(5.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                click()
            }

        ,
        painter = painterResource(Res.drawable.ic_favorite),
        contentDescription = null,
        tint = if (state) Color.Red else Color.Black
    )
}

