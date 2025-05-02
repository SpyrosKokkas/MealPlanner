package com.s3nko.mealplanner.ui.registerScreen

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
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.s3nko.mealplanner.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    navigateToMealsScreen: () -> Unit,
    navigateToLoginScreen: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVer = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val passwordVisibilityVer = remember { mutableStateOf(false) }


    LaunchedEffect(viewModel.stateEvents) {
        viewModel.stateEvents.collectLatest {
            when (it) {
                is RegisterEvents.NavigateToMeals -> {
                    navigateToMealsScreen()
                }

                is RegisterEvents.ShowError -> {
                    val errorMessage = it.message
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

                }

                null -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Meal Planner",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = R.drawable.ic_meal_logo),
                contentDescription = "App Center Logo",
                modifier = Modifier
                    .size(250.dp)
            )

            Text(
                text = "User Registration",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )


            Spacer(modifier = Modifier.padding(42.dp))


            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = {
                    Text(
                        text = "Email"
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.padding(16.dp))
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
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = passwordVer.value,
                onValueChange = { passwordVer.value = it },
                label = {
                    Text(
                        text = "Verify Password"
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                visualTransformation = if (passwordVisibilityVer.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (!passwordVisibilityVer.value)
                        painterResource(id = R.drawable.ic_visibility)
                    else
                        painterResource(id = R.drawable.ic_visibility_hide)

                    IconButton(onClick = {
                        passwordVisibilityVer.value = !passwordVisibilityVer.value
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = if (passwordVisibilityVer.value)
                                "Hide password"
                            else
                                "Show password"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.padding(24.dp))
            Button(onClick = {
                viewModel.register(
                    username = email.value,
                    password = password.value,
                    passwordVer = passwordVer.value
                )
            }) {
                Text(
                    text = "Register"
                )
            }
            Row {
                Text(
                    text = "Already have an account? Login "
                )
                Text(
                    text = "Here",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { navigateToLoginScreen() }

                )
            }


        }
    }
}
