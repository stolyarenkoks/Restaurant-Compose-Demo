package com.sks.littlelemon.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.sks.littlelemon.R
import com.sks.littlelemon.ui.theme.LittleLemonColor

private const val TAG = "LITTLE_LEMON_LOGIN"

// MARK: - View

@Composable
fun LoginScreen(onUserSignedIn: () -> Unit) {
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
            Log.d(TAG, "=== LOGIN SCREEN: Calling onUserSignedIn callback ===")
            onUserSignedIn()
        }
    )
}

// MARK: - Private View Components

@Composable
fun LoginView(
    username: String = "",
    password: String = "",
    onUsernameChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onUserSignedIn: () -> Unit = {}
) {
    val context = LocalContext.current
    val invalidCredentialsMessage = stringResource(R.string.invalid_credentials)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                if (validateCredentials(username, password)) {
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
    }
}

// MARK: - Private Methods

private fun validateCredentials(
    username: String,
    password: String
): Boolean {
    return username == "" && password == "1"
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginViewPreview() {
    LoginView()
}