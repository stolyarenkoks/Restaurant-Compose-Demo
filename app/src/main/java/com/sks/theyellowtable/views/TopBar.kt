package com.sks.theyellowtable.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sks.theyellowtable.R
import com.sks.theyellowtable.ui.theme.TheYellowTableColor

// MARK: - View

@Composable
fun TopBar(
    showBackButton: Boolean = false,
    showCartIcon: Boolean = false,
    onBackClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
            .background(TheYellowTableColor.lightGray),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showBackButton) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.size(24.dp)
                )
            }
        } else {
            IconButton(onClick = {}) {}
        }
        
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.the_yellow_table_logo),
            modifier = Modifier.fillMaxWidth(0.5F)
                .padding(horizontal = 56.dp)
        )
        
        if (showCartIcon) {
            IconButton(onClick = onCartClick) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = stringResource(R.string.cart),
                    modifier = Modifier.size(24.dp)
                )
            }
        } else {
            IconButton(onClick = {}) {}
        }
    }
}

// MARK: - Preview

@Preview
@Composable
private fun TopBarPreview() {
    TopBar()
}

@Preview
@Composable
private fun TopBarWithBackButtonPreview() {
    TopBar(showBackButton = true)
}

@Preview
@Composable
private fun TopBarWithCartPreview() {
    TopBar(showCartIcon = true)
}
