package ru.fbear.tmdbviewer.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.fbear.tmdbviewer.R
import ru.fbear.tmdbviewer.ui.theme.TMDBviewerTheme

enum class LoginStatus {
    None,
    Login,
    Successful,
    Unsuccessful
}

@Composable
fun ProfileLogin(
    onLoginRequest: suspend (login: String, password: String) -> Boolean
) {
    var login by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf<String?>(null) }

    var loginStatus by remember { mutableStateOf(LoginStatus.None) }

    var passwordIsVisible by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val auth = {
        if (login?.isNotEmpty() == true && password?.isNotEmpty() == true) {
            loginStatus = LoginStatus.Login
            coroutineScope.launch {
                loginStatus = if (onLoginRequest(login!!, password!!)) LoginStatus.Successful
                else LoginStatus.Unsuccessful
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = login ?: "",
                onValueChange = { login = it },
                label = { Text(text = stringResource(R.string.username)) },
                isError = login?.isEmpty() ?: false,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                maxLines = 1
            )
            OutlinedTextField(
                value = password ?: "",
                onValueChange = { password = it },
                label = { Text(text = stringResource(R.string.password)) },
                trailingIcon = {
                    IconButton(onClick = { passwordIsVisible = !passwordIsVisible }) {
                        Icon(
                            imageVector = if (passwordIsVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null
                        )
                    }
                },
                isError = password?.isEmpty() ?: false,
                visualTransformation = if (passwordIsVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { auth() }),
                singleLine = true,
                maxLines = 1
            )
            IconButton(onClick = auth) {
                Icon(
                    imageVector = Icons.Filled.Login,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }

            when (loginStatus) {
                LoginStatus.None -> Unit
                LoginStatus.Login -> CircularProgressIndicator()
                LoginStatus.Successful -> {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
                LoginStatus.Unsuccessful -> Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "ru lang",
    group = "language",
    locale = "RU"
)
@Preview(
    name = "en lang",
    group = "language",
    locale = "EN"
)
@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f
)
@Composable
fun ProfileLoginPreview() {
    TMDBviewerTheme {
        ProfileLogin(
            onLoginRequest = { _: String, _: String -> return@ProfileLogin true }
        )
    }
}