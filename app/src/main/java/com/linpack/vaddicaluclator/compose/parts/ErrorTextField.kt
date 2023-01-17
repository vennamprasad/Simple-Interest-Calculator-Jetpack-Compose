package com.linpack.vaddicaluclator.compose.parts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ErrorTextField(
    state: ErrorTextFieldState,
    placeholderText: String,
    modifier: Modifier
) {
    Column {
        val error = state.error
        OutlinedTextField(
            value = state.text,
            label = { Text(text = placeholderText.capitalize(Locale.ENGLISH))},
            onValueChange = { state.updateText(it) },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                errorCursorColor = Color.Red
            ),
            singleLine = true,
            isError = error != null,
            keyboardActions = KeyboardActions {
                state.validate()
            },
            modifier = modifier,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        if (error != null) {
            Text(
                error,
                color = Color.Red,
            )
        }
    }
}