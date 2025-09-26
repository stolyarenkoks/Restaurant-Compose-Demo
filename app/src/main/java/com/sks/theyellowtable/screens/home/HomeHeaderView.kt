package com.sks.theyellowtable.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sks.theyellowtable.R
import com.sks.theyellowtable.ui.theme.TheYellowTableColor

// MARK: - View

@Composable
fun HomeHeaderView(
    onMenuTapped: () -> Unit = {},
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(TheYellowTableColor.green)
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = TheYellowTableColor.yellow
        )

        Row(
            modifier = Modifier
                .padding(top = 18.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.location),
                    fontSize = 24.sp,
                    color = TheYellowTableColor.cloud,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )

                Text(
                    text = stringResource(id = R.string.description),
                    color = TheYellowTableColor.cloud,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(0.6f)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.upperpanelimage),
                contentDescription = stringResource(R.string.upper_panel_image),
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
            )
        }

        Button(
            onClick = {
                onMenuTapped()
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TheYellowTableColor.yellow)
        ) {
            Text(
                text = stringResource(id = R.string.order_button_text),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TheYellowTableColor.green
            )
        }
    }
}

// MARK: - Preview

@Preview
@Composable
private fun HomeHeaderPreview() {
    HomeHeaderView()
}
