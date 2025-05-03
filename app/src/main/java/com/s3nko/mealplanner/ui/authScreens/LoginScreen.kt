package com.s3nko.mealplanner.ui.authScreens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s3nko.mealplanner.R
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen (
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToMealsScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit
){

    val email = remember{ mutableStateOf("") }
    val password = remember{mutableStateOf("")}
    val context = LocalContext.current
    val passwordVisibility = remember { mutableStateOf(false) }

    // Observe the state events from the ViewModel
    LaunchedEffect(viewModel.authEvents) {
        viewModel.authEvents.collectLatest {
            when (it){
                is AuthEvents.NavigateToMeals -> {
                    navigateToMealsScreen()
                }

                is AuthEvents.ShowError -> {
                    val message = it.message
                    Toast.makeText(context , message, Toast.LENGTH_LONG).show()
                }

                null -> Unit
            }
        }

    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    ) {

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Text(
                text = "Meal Planner",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = R.drawable.ic_meal_logo),
                contentDescription = "App Center Logo",
                modifier = Modifier.size(250.dp)
            )

            Text (
                text = "User Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(96.dp))



            // Email text field
            OutlinedTextField(
                value = email.value,
                onValueChange ={email.value = it },
                label = {
                    Text (
                        text = "Email",
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.size(24.dp))

            //Password text field
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = {
                    Text(
                        text = "Password"
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (!passwordVisibility.value)
                        painterResource(id = R.drawable.ic_visibility)
                    else
                        painterResource(id = R.drawable.ic_visibility_hide)

                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = if (passwordVisibility.value)
                                "Hide password"
                            else
                                "Show password"
                        )
                    }
                }

            )

            Spacer(modifier = Modifier.size(24.dp))

            //Login Button
            Button(
                onClick = {
                    viewModel.login(username = email.value, password = password.value)
                }
            ){
                Text(
                    text = "Login"
                )
            }

            Row {
                Text(
                    text = "Don't have an account? Register "
                )
                Text(
                    text = "Here",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { navigateToRegisterScreen() }

                )
            }
        }
    }
}
