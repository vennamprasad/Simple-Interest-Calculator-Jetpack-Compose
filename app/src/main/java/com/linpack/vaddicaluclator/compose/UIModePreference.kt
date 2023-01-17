package com.linpack.vaddicaluclator.compose

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UIModePreference(context: Context) {
 //1
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "ui_language_preference"
    )
 //2
    suspend fun saveToDataStore(language: String) {
        dataStore.edit { preferences ->
            preferences[UI_MAN] = language
        }
    }
 //3
    val uiLang: Flow<String> = dataStore.data
        .map { preferences ->
            val uiMode = preferences[UI_MAN] ?: "en"
            uiMode
        }
 //4
    companion object {
        private val UI_MAN = preferencesKey<String>("ui_language")
    }

}