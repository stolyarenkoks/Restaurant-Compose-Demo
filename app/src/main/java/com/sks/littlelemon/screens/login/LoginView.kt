package com.sks.littlelemon.screens.login

import android.widget.Toast
import androidx.compose.animation.core.AnimationSpec
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sks.littlelemon.R
import com.sks.littlelemon.ui.theme.LittleLemonColor
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.draw.alpha

// MARK: - View

@Composable
fun LoginView(
    onUserSignedIn: () -> Unit,
    viewModel: LoginViewModel = viewModel()
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
            onUserSignedIn()
        },
        viewModel = viewModel
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
    viewModel: LoginViewModel
) {
    val context = LocalContext.current
    val invalidCredentialsMessage = stringResource(R.string.invalid_credentials)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1.0f))

        Image(
            painter = painterResource(id = R.drawable.logo_vertical),
            contentDescription = "Logo Image",
            modifier = Modifier.padding(10.dp)
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
                if (viewModel.validateCredentials(username, password)) {
                    onUserSignedIn()
                } else {
                    Toast.makeText(context, invalidCredentialsMessage, Toast.LENGTH_LONG).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                LittleLemonColor.green
            ),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = stringResource(R.string.login),
                color = LittleLemonColor.cloud
            )
        }

        Spacer(modifier = Modifier.weight(1.0f))

        HintText(showHint = viewModel.showHint)
    }
}

@Composable
private fun HintText(showHint: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (showHint) 1f else 0f
    )

    Text(
        text = "Hint: Use Any Username and '1234' as Password",
        color = LittleLemonColor.green,
        modifier = Modifier
            .padding(bottom = 0.dp)
            .alpha(alpha)
    )
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginPreview() {
    val viewModel: LoginViewModel = viewModel()
    LoginView(viewModel = viewModel)
}