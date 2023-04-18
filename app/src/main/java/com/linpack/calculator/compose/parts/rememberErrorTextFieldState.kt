package com.linpack.calculator.compose.parts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberErrorTextFieldState(
    initialText: String,
    validate: (String) -> String? = { null },
): ErrorTextFieldState {
    return rememberSaveable(saver = ErrorTextFieldState.Saver(validate)) {
        ErrorTextFieldState(initialText, validate)
    }
}

open class ErrorTextFieldState(
    initialText: String,
    private val validator: (String) -> String?,
) {
    var text by mutableStateOf(initialText)
    var error by mutableStateOf<String?>(null)
        protected set
    fun updateText(newValue: String) {
        text = newValue
        error = null
    }
    fun validate() {
        error = validator(text)
    }
    companion object {
        fun Saver(
            validate: (String) -> String?,
        ) = androidx.compose.runtime.saveable.Saver<ErrorTextFieldState, String>(
            save = { it.text },
            restore = { ErrorTextFieldState(it, validate) }
        )
    }
}