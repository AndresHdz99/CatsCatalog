package org.catsproject.project.ui.navigation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import catscatalog.composeapp.generated.resources.Res
import catscatalog.composeapp.generated.resources.ic_password_off
import catscatalog.composeapp.generated.resources.ic_password_on
import catscatalog.composeapp.generated.resources.logo_cat
import org.koin.compose.koinInject
import org.catsproject.project.ui.viewmodel.LoginViewModel
import org.jetbrains.compose.resources.painterResource


@Composable
fun LoginScreen(viewModel: LoginViewModel = koinInject<LoginViewModel>(), login:()->Unit) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var passwordHidden by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }



    if (viewModel.isLoggedIn) {
        LaunchedEffect(Unit) {
            login()
        }
    }else{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                painter = painterResource(Res.drawable.logo_cat),
                contentDescription = null,
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .heightIn(max = 400.dp)
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)

            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .heightIn(max = 700.dp)
                    .fillMaxWidth(0.7f),
                value = uiState.user,
                onValueChange = { viewModel.onUserChange(it)},
                label = { Text("User") },
                shape = RoundedCornerShape(25),
                singleLine = true
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .heightIn(max = 700.dp)
                    .fillMaxWidth(0.7f),
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password") },
                shape = RoundedCornerShape(25), // Faltaba .dp
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    if (passwordHidden)
                        Icon(
                            painter = painterResource(Res.drawable.ic_password_on),
                            contentDescription = null,
                            modifier = Modifier.height(20.dp).clickable(
                                interactionSource = interactionSource,
                                indication = null) {
                                passwordHidden = !passwordHidden
                            })
                    else
                        Icon(painter = painterResource(Res.drawable.ic_password_off),
                            contentDescription = null,
                            modifier = Modifier.height(20.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    passwordHidden = !passwordHidden
                                })
                }
            )

            viewModel.errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

            Spacer(Modifier.height(20.dp))


            Button({
                viewModel.login()
            }, enabled = uiState.inLoginEnable){
                Text("Login")
            }

        }
    }

}

