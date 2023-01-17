package com.linpack.vaddicaluclator.compose

import android.content.Context
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

@OptIn(DelicateCoroutinesApi::class)
fun setLocaleLang(lang: String, context: Context) {
    val locale = Locale(lang)
    Locale.setDefault(locale)
    val resources = context.resources
    val configuration = resources.configuration
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
    val uiDataStore = UIModePreference(context)
    GlobalScope.launch {
        uiDataStore.saveToDataStore(lang)
    }
}