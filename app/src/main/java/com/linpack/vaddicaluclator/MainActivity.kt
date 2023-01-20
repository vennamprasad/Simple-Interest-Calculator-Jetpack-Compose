@file:OptIn(DelicateCoroutinesApi::class)

package com.linpack.vaddicaluclator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.linpack.vaddicaluclator.compose.SIC
import com.linpack.vaddicaluclator.compose.UIModePreference
import com.linpack.vaddicaluclator.compose.setLocaleLang
import com.linpack.vaddicaluclator.ui.theme.CalculatorTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uiDataStore = UIModePreference(this)
        setContent {
            CalculatorTheme {
                Scaffold(
                    topBar = {
                        HomeTopAppBar()
                    },
                    content = {
                        SIC(it)
                    }
                )
            }
        }
        GlobalScope.launch {
            setLocaleLang("te", this@MainActivity)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
        modifier = modifier
    )
}

