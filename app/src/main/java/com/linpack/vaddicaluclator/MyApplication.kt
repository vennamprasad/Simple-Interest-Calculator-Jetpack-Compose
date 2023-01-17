package com.linpack.vaddicaluclator

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics

class MyApplication : Application() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    override fun onCreate() {
        super.onCreate()
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
}