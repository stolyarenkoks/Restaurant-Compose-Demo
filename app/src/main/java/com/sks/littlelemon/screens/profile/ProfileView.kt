package com.sks.littlelemon.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sks.littlelemon.R
import com.sks.littlelemon.views.TopBar
import com.sks.littlelemon.ui.theme.LittleLemonColor

// MARK: - View

@Composable
fun ProfileView(
    viewModel: ProfileViewModel = viewModel(),
    onUserSignedOut: () -> Unit
) {
    Scaffold(
        topBar = { TopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.profile),
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onUserSignedOut,
                colors = ButtonDefaults.buttonColors(
                    LittleLemonColor.red
                ),
            ) {
                Text(
                    text = stringResource(R.string.sign_out),
                    color = LittleLemonColor.cloud
                )
            }
        }
    }
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProfilePreview() {
    ProfileView(
        onUserSignedOut = {}
    )
} 