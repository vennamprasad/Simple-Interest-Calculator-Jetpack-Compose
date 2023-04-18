package com.linpack.calculator.compose

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.linpack.calculator.compose.parts.*
import com.linpack.interest.calc.R
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
    val mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
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

    fun clear() {
        if (principalState.text.isNotEmpty() && interestState.text.isNotEmpty() && fromDateState.text.isNotEmpty() && toDateState.text.isNotEmpty()) {
            principalState.text = ""
            interestState.text = ""
            fromDateState.text = ""
            toDateState.text = ""
            interestPerMonthState.value = ""
            totalDaysState.value = ""
            totalMonthsState.value = ""
            totalState.value = ""
            totalInterest.value = ""
            keyboardController?.hide()
        }

    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ErrorTextField(
            state = principalState,
            placeholderText = stringResource(R.string.p),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            maxChar = 10
        )
        Spacer(modifier = Modifier.height(8.dp))
        ErrorTextField(
            state = interestState,
            placeholderText = stringResource(R.string.ir),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            maxChar = 1,
        )
        Spacer(modifier = Modifier.height(8.dp))
        ReadonlyTextField(state = fromDateState,
            placeholderText = stringResource(R.string.fd),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            onClick = {
                DatePickerDialog(
                    context,
                    fromDatePickerDialogListener,
                    fromCal.get(Calendar.YEAR),
                    fromCal.get(Calendar.MONTH),
                    fromCal.get(Calendar.DAY_OF_MONTH)
                ).show()
            })
        Spacer(modifier = Modifier.height(8.dp))
        ReadonlyTextField(state = toDateState,
            placeholderText = stringResource(R.string.td),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            onClick = {
                DatePickerDialog(
                    context,
                    toDatePickerDialogListener,
                    toCal.get(Calendar.YEAR),
                    toCal.get(Calendar.MONTH),
                    toCal.get(Calendar.DAY_OF_MONTH)
                ).show()
            })
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    clear()
                }
            ) {
                Text(stringResource(R.string.clear))
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    action(
                        keyboardController,
                        principalState,
                        interestState,
                        fromDateState,
                        toDateState,
                        totalDaysState,
                        totalMonthsState,
                        interestPerMonthState,
                        totalInterest,
                        totalState,
                        context,
                        mFirebaseAnalytics
                    )
                },
            ) {
                Text(stringResource(R.string.calc))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(buildString {
            append(stringResource(R.string.total_days))
            append(SPACE)
            append(totalDaysState.value)
        })
        Spacer(modifier = Modifier.height(8.dp))
        Text(buildString {
            append(stringResource(R.string.total_months))
            append(SPACE)
            append(totalMonthsState.value)
        })
        Spacer(modifier = Modifier.height(8.dp))
        Text(buildString {
            append(stringResource(R.string.interest_per_month))
            append(SPACE)
            append(interestPerMonthState.value)
        })
        Spacer(modifier = Modifier.height(8.dp))
        Text(buildString {
            append(stringResource(R.string.total_interest))
            append(SPACE)
            append(totalInterest.value)
        })
        Spacer(modifier = Modifier.height(8.dp))
        Text(buildString {
            append(stringResource(R.string.total_sip))
            append(SPACE)
            append(totalState.value)
        })
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private fun action(
    keyboardController: SoftwareKeyboardController?,
    principalState: ErrorTextFieldState,
    interestState: ErrorTextFieldState,
    fromDateState: ErrorTextFieldState,
    toDateState: ErrorTextFieldState,
    totalDaysState: MutableState<String>,
    totalMonthsState: MutableState<String>,
    interestPerMonthState: MutableState<String>,
    totalInterest: MutableState<String>,
    totalState: MutableState<String>,
    context: Context,
    mFirebaseAnalytics: FirebaseAnalytics,
) {
    keyboardController?.hide()
    listOf(
        principalState, interestState, fromDateState, toDateState
    ).forEach(ErrorTextFieldState::validate)
    if (principalState.text.isNotBlank() && interestState.text.isNotBlank() && fromDateState.text.isNotBlank() && toDateState.text.isNotBlank()) {
        val principal = principalState.text.toDouble()
        val rate = interestState.text.toDouble()
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        val f = LocalDate.parse(fromDateState.text, formatter)
        val t = LocalDate.parse(toDateState.text, formatter)
        val days = ChronoUnit.DAYS.between(f, t)
        val months = days / 30.44
        val interest = ((principal * rate * months) / 100)
        val total = principal + interest
        totalDaysState.value = days.toString()
        totalMonthsState.value = String.format("%.2f", months)
        interestPerMonthState.value = String.format("%.2f", interest / months)
        totalInterest.value = String.format("%.2f", interest)
        totalState.value = String.format("%.2f", total)

        //firebase
        val bundle = Bundle()
        bundle.putString(
            "device_name",
            Settings.Global.getString(context.contentResolver, "device_name")
        )
        bundle.putString(context.getString(R.string.p), principal.toString())
        bundle.putString(context.getString(R.string.ir), rate.toString())
        bundle.putString(
            context.getString(R.string.total_days),
            totalDaysState.value
        )
        bundle.putString(
            context.getString(R.string.total_months), totalMonthsState.value
        )
        bundle.putString(
            context.getString(R.string.total_interest), totalInterest.value
        )
        bundle.putString(context.getString(R.string.total_sip), totalState.value)
        bundle.putString(
            context.getString(R.string.interest_per_month),
            interestPerMonthState.value
        )
        mFirebaseAnalytics.logEvent(context.getString(R.string.app_name), bundle)
    }
}


