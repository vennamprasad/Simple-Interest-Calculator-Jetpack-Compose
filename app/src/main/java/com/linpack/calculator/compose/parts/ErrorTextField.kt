package com.linpack.calculator.compose.parts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorTextField(
    state: ErrorTextFieldState,
    placeholderText: String,
    modifier: Modifier,
    maxChar: Int
) {
    Column {
        val error = state.error
        OutlinedTextField(
            value = state.text,
            label = { Text(text = placeholderText.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ENGLISH
                ) else it.toString()
            }) },
            onValueChange = {
                if (it.length <= maxChar)  state.updateText(it)
            },
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