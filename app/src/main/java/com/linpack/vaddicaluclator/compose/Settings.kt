package com.linpack.vaddicaluclator.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen() {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    Scaffold { innerPadding ->
        val contentModifier = Modifier
            .padding(innerPadding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)

        val context = LocalContext.current
        val langList = arrayOf(
            "Hindi-हिंदी",
            "English",
            "Telugu-తెలుగు",
            "Tamil-தமிழ்",
            "Kannada-ಕನ್ನಡ",
            "Malayalam-മലയാളം"
        )
        var items by remember {
            mutableStateOf(
                langList.map {
                    LanguageItem(
                        title = it,
                        isSelected = false
                    )
                }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(vertical = 56.dp)
        ) {
            items(items.size) { i ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            items = items.mapIndexed { j, item ->
                                if (i == j) {
                                    item.copy(isSelected = true)
                                } else item.copy(isSelected = false)
                            }
                            when (i) {
                                0 -> {
                                    setLocaleLang("hi", context)
                                }
                                1 -> {
                                    setLocaleLang("en", context)
                                }
                                2 -> {
                                    setLocaleLang("te", context)
                                }
                                3 -> {
                                    setLocaleLang("ta", context)
                                }
                                4 -> {
                                    setLocaleLang("kn", context)
                                }
                                5 -> {
                                    setLocaleLang("ml", context)
                                }
                                else -> {
                                    setLocaleLang("en", context)
                                }
                            }
                        }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = items[i].title, fontSize = 20.sp)
                    if (items[i].isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.Blue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
            }
        }
    }
}

data class LanguageItem(val title: String, val isSelected: Boolean)