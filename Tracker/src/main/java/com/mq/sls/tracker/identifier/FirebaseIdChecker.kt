package com.mq.sls.tracker.identifier

import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.mq.sls.tracker.SLSReporter

class FirebaseIdChecker : IdChecker {

    private var hasInit = false
    private var enable = false

    override fun isEnable(): Boolean {
        if (hasInit) {
            return enable
        }
        enable = try {
            Class.forName("com.google.firebase.analytics.FirebaseAnalytics")
            true
        } catch (e: Exception) {
            false
        }
        hasInit = true
        return enable
    }

    private val firebase by lazy {
        FirebaseAnalytics.getInstance(SLSReporter.instance.getApp())
    }

    override fun checkIdentifier(callback: (String) -> Unit) {
        SLSReporter.slsDebugLog("Firebase.checkIdentifier() isEnable: ${isEnable()}, called with: callback = $callback")
        if (isEnable()) {
            firebase.appInstanceId.addOnCompleteListener {
                SLSReporter.slsDebugLog("Firebase.addOnCompleteListener() called with: callback = ${it.result}")
                callback(it.result)
            }
        } else {
            callback("")
        }
    }
}