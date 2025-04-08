import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sks.littlelemon.R
import com.sks.littlelemon.screens.home.LowerPanel
import com.sks.littlelemon.screens.home.UpperPanel

// MARK: - View

@Composable
fun HomeScreen(onUserSignedOut: () -> Unit) {
    HomeView(onUserSignedOut = onUserSignedOut)
}

// MARK: - Private View Components

@Composable
private fun HomeView(onUserSignedOut: () -> Unit = {}) {
    Column() {
        UpperPanel()
        LowerPanel()

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onUserSignedOut()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7A0101)),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.logout),
                color = Color(0xFFFFFFFF)
            )
        }
    }
}

// MARK: - Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeViewPreview() {
    HomeView()
}