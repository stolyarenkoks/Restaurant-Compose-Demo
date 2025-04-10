package com.sks.littlelemon.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StepperView(
    minStepValue: Int = 0,
    maxStepValue: Int  = 10,
    count: Int = 0,
    onAdd: () -> Unit = {},
    onRemove: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                if (count > minStepValue) {
                    onRemove()
                }
            }
        ) {
            Text("-")
        }

        Text("$count")

        Button(
            onClick = {
                if (count < maxStepValue) onAdd()
            }
        ) {
            Text("+")
        }
    }
}

@Preview(name = "StepperView")
@Composable
fun MainPreviewUserSignedIn() {
    StepperView()
}