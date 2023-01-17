@file:OptIn(DelicateCoroutinesApi::class)

package com.linpack.vaddicaluclator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import com.linpack.vaddicaluclator.compose.SIC
import com.linpack.vaddicaluclator.compose.UIModePreference
import com.linpack.vaddicaluclator.compose.setLocaleLang
import com.linpack.vaddicaluclator.ui.theme.VaddiCaluclatorTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uiDataStore = UIModePreference(this)
        GlobalScope.launch {
            setLocaleLang(uiDataStore.uiLang.first(),applicationContext)
        }
        setContent {
           VaddiCaluclatorTheme {
               Scaffold(
                   content = {
                       SIC(it)
                   }
               )
           }
        }
    }
}