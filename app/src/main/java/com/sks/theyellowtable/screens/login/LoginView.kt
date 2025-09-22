package com.sks.theyellowtable.screens.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.sks.theyellowtable.R
import com.sks.theyellowtable.ui.theme.TheYellowTableColor
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.draw.alpha

// MARK: - View

@Composable
fun LoginView(
    onUserSignedIn: (String) -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    LoginView(
        username = username,
        password = password,
        onUsernameChanged = { username = it },
        onPasswordChanged = { password = it },
        onUserSignedIn = {
            onUserSignedIn(username)
        },
        onValidateCredentials = { user, pass ->
            viewModel.validateCredentials(user, pass)
        },
        showHint = viewModel.showHint
    )
}

// MARK: - Private View Components

@Composable
private fun LoginView(
    username: String = "",
    password: String = "",
    onUsernameChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onUserSignedIn: () -> Unit = {},
    onValidateCredentials: (String, String) -> Boolean = { _, _ -> false },
    showHint: Boolean = false
) {
    val context = LocalContext.current
    val invalidCredentialsMessage = stringResource(R.string.invalid_credentials)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo Image",
            modifier = Modifier.padding(40.dp)
        )

        TextField(
            value = username,
            onValueChange = onUsernameChanged,
            label = { Text(text = stringResource(R.string.username)) },
            modifier = Modifier.padding(10.dp)
        )

        TextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = { Text(text = stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(10.dp),
        )

        Button(
            onClick = {
                if (onValidateCredentials(username, password)) {
                    onUserSignedIn()
                } else {
                    Toast.makeText(context, invalidCredentialsMessage, Toast.LENGTH_LONG).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                TheYellowTableColor.green
            ),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = stringResource(R.string.login),
                color = TheYellowTableColor.cloud
            )
        }

        Spacer(modifier = Modifier.weight(1.0f))

        HintText(showHint = showHint)
    }
}

@Composable
private fun HintText(showHint: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (showHint) 1f else 0f
    )

    Text(
        text = stringResource(R.string.hint_password),
        color = TheYellowTableColor.green,
        modifier = Modifier
            .padding(bottom = 0.dp)
            .alpha(alpha)
    )
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginPreview() {
    LoginView(
        username = "demo",
        password = "",
        onUsernameChanged = {},
        onPasswordChanged = {},
        onUserSignedIn = {},
        onValidateCredentials = { _, _ -> false },
        showHint = true
    )
}