package com.linpack.vaddicaluclator.compose

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.linpack.vaddicaluclator.R
import com.linpack.vaddicaluclator.compose.parts.ErrorTextField
import com.linpack.vaddicaluclator.compose.parts.ErrorTextFieldState
import com.linpack.vaddicaluclator.compose.parts.ReadonlyTextField
import com.linpack.vaddicaluclator.compose.parts.rememberErrorTextFieldState
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

const val SPACE = " "
const val DATE_FORMAT = "dd-MM-yyyy"
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SIC(paddingValues: PaddingValues) {
    val context = LocalContext.current
    val principalState = rememberErrorTextFieldState("", validate = { text ->
        when {
            text.isEmpty() -> {
                context.getString(R.string.pep)
            }
            else -> null
        }
    })
    val interestState = rememberErrorTextFieldState("", validate = { text ->
        when {
            text.isEmpty() -> {
                context.getString(R.string.pei)
            }
            else -> null
        }
    })
    val fromDateState = rememberErrorTextFieldState("", validate = { text ->
        when {
            text.isEmpty() -> {
                context.getString(R.string.pfd)
            }
            else -> null
        }
    })
    val toDateState = rememberErrorTextFieldState("", validate = { text ->
        when {
            text.isEmpty() -> {
                context.getString(R.string.ptd)
            }
            else -> null
        }
    })
    val interestPerMonthState = remember { mutableStateOf("") }
    val totalDaysState = remember { mutableStateOf("") }
    val totalMonthsState = remember { mutableStateOf("") }
    val totalState = remember { mutableStateOf("") }
    val totalInterest = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val fromCal = Calendar.getInstance()
    val toCal = Calendar.getInstance()
    val fromDatePickerDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            fromCal.set(Calendar.YEAR, year)
            fromCal.set(Calendar.MONTH, monthOfYear)
            fromCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = DATE_FORMAT
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            fromDateState.text = sdf.format(fromCal.time)
        }
    val toDatePickerDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            toCal.set(Calendar.YEAR, year)
            toCal.set(Calendar.MONTH, monthOfYear)
            toCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = DATE_FORMAT
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            toDateState.text = sdf.format(toCal.time)
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()).padding(12.dp)
    ) {
        ErrorTextField(
            state = principalState,
            placeholderText = stringResource(R.string.p),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        ErrorTextField(
            state = interestState,
            placeholderText = stringResource(R.string.ir),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        ReadonlyTextField(
            state = fromDateState,
            placeholderText = stringResource(R.string.fd),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            onClick = {
                DatePickerDialog(
                    context, fromDatePickerDialogListener,
                    fromCal.get(Calendar.YEAR),
                    fromCal.get(Calendar.MONTH),
                    fromCal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        ReadonlyTextField(
            state = toDateState,
            placeholderText = stringResource(R.string.td),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            onClick = {
                DatePickerDialog(
                    context, toDatePickerDialogListener,
                    toCal.get(Calendar.YEAR),
                    toCal.get(Calendar.MONTH),
                    toCal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                keyboardController?.hide()
                listOf(
                    principalState,
                    interestState,
                    fromDateState,
                    toDateState
                ).forEach(ErrorTextFieldState::validate)
                if (principalState.text.isNotBlank()
                    && interestState.text.isNotBlank()
                    && fromDateState.text.isNotBlank()
                    && toDateState.text.isNotBlank()
                ) {
                    val principal = principalState.text.toDouble()
                    val rate = interestState.text.toDouble()
                    val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
                    val f = LocalDate.parse(fromDateState.text, formatter)
                    val t = LocalDate.parse(toDateState.text, formatter)
                    val days = ChronoUnit.DAYS.between(f, t)
                    val months = days / 30.44
                    val i = ((principal * rate * months) / 100)
                    val total = principal + i
                    totalDaysState.value = days.toString()
                    totalMonthsState.value = String.format("%.2f", months)
                    interestPerMonthState.value = String.format("%.2f", i / months)
                    totalInterest.value = String.format("%.2f", i)
                    totalState.value = String.format("%.2f", total)

                }
            },
        ) {
            Text(stringResource(R.string.calc))
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(modifier = Modifier
            .align(Alignment.Start)
            .fillMaxWidth(), onClick = { }) {
            Text(buildString {
                append(stringResource(R.string.total_days))
                append(SPACE)
                append(totalDaysState.value)
            })
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(modifier = Modifier
            .align(Alignment.Start)
            .fillMaxWidth(), onClick = { }) {
            Text(buildString {
                append(stringResource(R.string.total_months))
                append(SPACE)
                append(totalMonthsState.value)
            })
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(modifier = Modifier
            .align(Alignment.Start)
            .fillMaxWidth(), onClick = { }) {
            Text(buildString {
                append(stringResource(R.string.interest_per_month))
                append(SPACE)
                append(interestPerMonthState.value)
            })
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(modifier = Modifier
            .align(Alignment.Start)
            .fillMaxWidth(), onClick = { }) {
            Text(buildString {
                append(stringResource(R.string.total_interest))
                append(SPACE)
                append(totalState.value)
            })
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(modifier = Modifier
            .align(Alignment.Start)
            .fillMaxWidth(), onClick = { }) {
            Text(buildString {
                append(stringResource(R.string.total_sip))
                append(SPACE)
                append(totalInterest.value)
            })
        }
    }
}

