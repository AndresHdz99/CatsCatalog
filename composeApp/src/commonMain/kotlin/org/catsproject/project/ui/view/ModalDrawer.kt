package org.catsproject.project.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import catscatalog.composeapp.generated.resources.Res
import catscatalog.composeapp.generated.resources.ic_favorite

import org.catsproject.project.ui.navigation.NavigationEnum
import org.catsproject.project.ui.theme.GrayBlur

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ModalDrawer(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    logOut: () -> Unit,
    favorite: () -> Unit,
    catalog: () -> Unit = {}
) {

    var taps by remember { mutableStateOf(NavigationEnum.CATALOG) }

    ModalNavigationDrawer(
        modifier = modifier.background(Color.White),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet() {
                Column(Modifier.padding(15.dp).weight(1f)) {

                    TabSelect(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .then(
                                if (taps == NavigationEnum.CATALOG) Modifier.background(GrayBlur) else Modifier
                            ),
                        text = "Catalog",
                        icon = Res.drawable.ic_favorite
                    ) {

                        if (taps != NavigationEnum.CATALOG) {
                            taps = NavigationEnum.CATALOG
                            catalog()
                        }

                    }

                    Spacer(Modifier.height(15.dp))

                    TabSelect(
                        modifier = Modifier.clip(RoundedCornerShape(20.dp))
                            .then(
                                if (taps == NavigationEnum.FAVORITE) Modifier.background(GrayBlur) else Modifier
                            ),
                        text = "Favorite",
                        icon = Res.drawable.ic_favorite
                    ) {
                        if (taps != NavigationEnum.FAVORITE) {
                            favorite()
                            taps = NavigationEnum.FAVORITE
                        }

                    }

                    Spacer(Modifier.fillMaxHeight().weight(1f))



                    Text(modifier = Modifier.fillMaxWidth().clickable {
                        logOut()
                    }, text = "Log out", textAlign = TextAlign.End, fontSize = 22.sp)

                }
            }
        }
    ) {
        content()
    }
}

@Composable
fun TabSelect(
    modifier: Modifier = Modifier,
    text: String,
    icon: DrawableResource,
    onClick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color.Red
        )

        Spacer(Modifier.width(15.dp))

        Text(text = text, fontSize = 22.sp)
    }
}