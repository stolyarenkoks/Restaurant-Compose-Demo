package com.sks.theyellowtable.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import com.sks.theyellowtable.R
import com.sks.theyellowtable.views.TopBar
import com.sks.theyellowtable.ui.theme.TheYellowTableColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize

// MARK: - View

@Composable
fun ProfileView(
    username: String,
    viewModel: ProfileViewModel = koinViewModel(),
    onUserSignedOut: () -> Unit
) {
    ProfileView(
        username = username,
        onUserSignedOut = onUserSignedOut
    )
}

// MARK: - Private View Components

@Composable
private fun ProfileView(
    username: String,
    onUserSignedOut: () -> Unit
) {
    Scaffold(
        topBar = { TopBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(TheYellowTableColor.yellow, shape = CircleShape)
                    .height(80.dp)
                    .width(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = username.take(1).uppercase(),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = TheYellowTableColor.green
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = username.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.profile),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onUserSignedOut,
                colors = ButtonDefaults.buttonColors(
                    TheYellowTableColor.red
                ),
            ) {
                Text(
                    text = stringResource(R.string.sign_out),
                    color = TheYellowTableColor.cloud
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
        username = "Konstantin",
        onUserSignedOut = {}
    )
} 