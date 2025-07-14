package org.catsproject.project.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import catscatalog.composeapp.generated.resources.Res
import catscatalog.composeapp.generated.resources.ic_password_off
import catscatalog.composeapp.generated.resources.ic_password_on
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun AlertFailCats(text:String,onClick:()->Unit) {

    var moreInformation by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth(0.7f)
        .shadow(
            elevation = 8.dp,               // tamaño de la sombra
            shape = RoundedCornerShape(16.dp), // forma de los bordes
            clip = true                    // opcional: recorta el contenido al shape
        )
        .clip(RoundedCornerShape(30.dp))

        .background(Color.White).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("An error occurred while \nfetching the information.", textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold,fontSize = 18.sp)


        Spacer(Modifier.height(20.dp))


        Text("Retry", fontSize = 14.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Blue.copy(0.5f))
                .padding(vertical = 10.dp)
                .padding(horizontal = 15.dp)
                .clickable {
                    onClick()
                }
            ,
            style = TextStyle(
                color = Color.White
            )

        )

        Spacer(Modifier.height(20.dp))

        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.BottomEnd){

            Icon(modifier = Modifier.size(25.dp).clickable {
                moreInformation = !moreInformation
            },painter = painterResource(
                if ( !moreInformation)
                Res.drawable.ic_password_on else Res.drawable.ic_password_off
            ), contentDescription = null, tint = Color.Red)


        }




       AnimatedVisibility(moreInformation) {
            Spacer(Modifier.height(20.dp))

            Text(text, fontSize = 12.sp,style = TextStyle(
                color = Color.Red
            )
            )
        }


    }
}