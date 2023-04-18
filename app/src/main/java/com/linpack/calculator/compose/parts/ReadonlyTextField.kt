package com.linpack.calculator.compose.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadonlyTextField(
    state: ErrorTextFieldState,
    placeholderText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box {
        Column {
            val error = state.error
            OutlinedTextField(
                value = state.text,
                onValueChange = { state.updateText(it) },
                label = { Text(text = placeholderText.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ENGLISH
                    ) else it.toString()
                }) },
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
            )
            if (error != null) {
                Text(
                    error,
                    color = Color.Red,
                )
            }
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}