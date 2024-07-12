package com.mq.sls.tracker.identifier

import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.mq.sls.tracker.SLSReporter

class FirebaseIdChecker : BaseIdChecker() {

    override fun checkEnable(): Boolean {
        return try {
            Class.forName("com.google.firebase.analytics.FirebaseAnalytics")
            true
        } catch (e: Exception) {
            false
        }
    }

    private val firebase by lazy {
        FirebaseAnalytics.getInstance(SLSReporter.instance.getApp())
    }

    override fun checkIdentifier(callback: (String) -> Unit): Boolean {
        SLSReporter.slsDebugLog("Firebase.checkIdentifier() isEnable: ${isEnable()}")
        if (super.checkIdentifier(callback)) {
            return true
        }
        firebase.appInstanceId.addOnCompleteListener {
            SLSReporter.slsDebugLog("Firebase.addOnCompleteListener() called with: callback = ${it.result}")
            callback(it.result)
        }
        return true
    }
}