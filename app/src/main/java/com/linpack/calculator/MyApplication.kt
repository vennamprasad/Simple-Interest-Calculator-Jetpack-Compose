package com.linpack.calculator

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics

class MyApplication : Application() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    override fun onCreate() {
        super.onCreate()
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }
}