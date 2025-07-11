package com.sks.littlelemon.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sks.littlelemon.ui.theme.LittleLemonColor

// MARK: - View

@Composable
fun StepperView(
    minStepValue: Int = 0,
    maxStepValue: Int  = 10,
    count: Int = 0,
    onAdd: () -> Unit = {},
    onRemove: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextButton(
            onClick = {
                if (count > minStepValue) {
                    onRemove()
                }
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.headlineMedium,
                color = LittleLemonColor.green
            )
        }

        Text(
            text = "$count",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        TextButton(
            onClick = {
                if (count < maxStepValue) {
                    onAdd()
                }
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.headlineMedium,
                color = LittleLemonColor.green
            )
        }
    }
}

// MARK: - Preview

@Preview
@Composable
private fun StepperPreview() {
    StepperView()
}