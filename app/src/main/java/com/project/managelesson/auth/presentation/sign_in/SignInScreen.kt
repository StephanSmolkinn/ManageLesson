package com.project.managelesson.auth.presentation.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.managelesson.core.navigation.Screen

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    val scaffoldState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                SignInViewModel.UiEvent.AuthError -> {

                }

                SignInViewModel.UiEvent.AuthSuccess -> {
                    navController.navigate(Screen.ProfileScreen.route) {
                        popUpTo(Screen.SignInScreen.route) { inclusive = true }
                    }
                }

                is SignInViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SignInTopBar(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(it)
        ) {
            OutlinedTextField(
                value = state.value.email,
                onValueChange = { viewModel.onEvent(SignInEvent.OnEmailChange(it)) },
                label = { Text(text = "Email") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value =  state.value.password,
                onValueChange = { viewModel.onEvent(SignInEvent.OnPasswordChange(it)) },
                label = { Text(text = "Password") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onEvent(SignInEvent.OnAuth) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 18.dp)
            ) {
                Text(
                    text = "Sign in",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(
                    text = "Not yet registered ?",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { navController.navigate(Screen.SignUpScreen.route) }
                ) {
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    )
}

