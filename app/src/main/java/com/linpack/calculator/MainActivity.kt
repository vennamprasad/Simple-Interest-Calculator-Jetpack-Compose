@file:OptIn(DelicateCoroutinesApi::class)

package com.linpack.calculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.linpack.calculator.compose.SIC
import com.linpack.calculator.compose.UIModePreference
import com.linpack.calculator.compose.setLocaleLang
import com.linpack.calculator.ui.theme.CalculatorTheme
import com.linpack.interest.calc.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val uiDataStore = UIModePreference(this)
    lateinit var uiLang: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Scaffold(
                    content = {
                        SIC(it)
                    }
                )
            }
        }
        lifecycleScope.launch {
            setLangPref(uiDataStore.uiLang.first())
        }
    }

    private fun setLangPref(language: String = "en") {
        GlobalScope.launch {
            setLocaleLang(language, this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ui_lang, menu)
        lifecycleScope.launch {
            uiLang = uiDataStore.uiLang.first()
            when (uiDataStore.uiLang.first()) {
                "hi" -> {
                    menu?.findItem(R.id.action_hindi)?.isChecked = true
                }

                "en" -> {
                    menu?.findItem(R.id.action_english)?.isChecked = true
                }

                "te" -> {
                    menu?.findItem(R.id.action_telugu)?.isChecked = true
                }

                "ta" -> {
                    menu?.findItem(R.id.action_tamil)?.isChecked = true
                }

                "ml" -> {
                    menu?.findItem(R.id.action_malayalam)?.isChecked = true
                }

                "kn" -> {
                    menu?.findItem(R.id.action_Kannada)?.isChecked = true
                }

                else -> {

                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_hindi -> {
                validateLang("hi")
            }

            R.id.action_Kannada -> {
                validateLang("kn")
            }

            R.id.action_english -> {
                validateLang("en")
            }

            R.id.action_tamil -> {
                validateLang("ta")
            }

            R.id.action_malayalam -> {
                validateLang("ml")
            }

            R.id.action_telugu -> {
                validateLang("te")
            }

            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateLang(lan: String) {
        if (uiLang != lan) {
            setLangPref(lan)
            restartAct()
        }
    }

    private fun restartAct() {
        val intent = intent
        finish()
        startActivity(intent)
    }

}

