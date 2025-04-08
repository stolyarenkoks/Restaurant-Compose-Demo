package com.sks.littlelemon.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sks.littlelemon.R

@Composable
fun LowerPanel() {
    Column {
        WeeklySpecialCard()
        MenuDish()
    }
}

@Composable
fun WeeklySpecialCard(){
    Text(
        text = "Weekly Special",
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(8.dp)
    )
}

@Composable
fun MenuDish() {
    var count by rememberSaveable {
        mutableIntStateOf(0)
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
        Column {
            Text(text = "Greek Salad",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "The famous greek salad of " +
                        "crispy lettuce, peppers, olives, " +
                        "our Chicago ...",
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .fillMaxWidth(.75f)
            )

            Text(
                text = "$12.99",
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }
        
        Image(
            painter = painterResource(id = R.drawable.greeksalad),
            contentDescription = "",
        )
    }

    Stepper(
        count = count,
        onAdd = { count++ },
        onRemove = { count-- }
    )

    HorizontalDivider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 1.dp,
        color = Color.LightGray
    )
}

@Composable
fun Stepper(
    minStepValue: Int = 0,
    maxStepValue: Int  = 10,
    count: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
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

@Preview(showBackground = true)
@Composable
fun LowerPanelPreview(){
    LowerPanel()
}