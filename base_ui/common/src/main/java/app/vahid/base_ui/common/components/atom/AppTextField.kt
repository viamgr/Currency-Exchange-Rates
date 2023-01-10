@file:OptIn(ExperimentalMaterial3Api::class)

package app.vahid.base_ui.common.components.atom

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import app.vahid.base_ui.theme.Theme

@Composable
fun AppTextField(
    value: String,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    onValueChange: (value: String) -> Unit = {},
    textColor: Color,
) {
    val focusRequester = remember { FocusRequester() }

    var text by remember(key1 = value) {
        mutableStateOf(TextFieldValue(
            text = value,
            selection = if (value == "0") {
                TextRange(start = 0, end = 1)
            } else {
                TextRange(start = value.length, end = value.length)
            }
        ))
    }

    OutlinedTextField(
        maxLines = 2,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { },
        value = text,
        textStyle = Theme.typography.bodySmall.copy(
            textAlign = TextAlign.End,
            color = textColor
        ),
        readOnly = readOnly,
        onValueChange = {
            if (it.text.length < 12) {
                text = it
                onValueChange(it.text)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Theme.colorScheme.secondary,
            errorCursorColor = Theme.colorScheme.secondary,
            focusedBorderColor = Theme.colorScheme.primaryBg,
            disabledBorderColor = Theme.colorScheme.primaryBg,
            unfocusedBorderColor = Theme.colorScheme.primaryBg,
            errorBorderColor = Theme.colorScheme.primaryBg,
        ),

        )
    LaunchedEffect(Unit) {
        if (!readOnly)
            focusRequester.requestFocus()
    }

}