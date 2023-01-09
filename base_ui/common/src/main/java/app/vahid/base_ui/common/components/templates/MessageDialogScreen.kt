package app.vahid.base_ui.common.components.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MessageDialogScreen(message: String) {
    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxSize(0.8F)) {
        Text(text = message)
    }
}