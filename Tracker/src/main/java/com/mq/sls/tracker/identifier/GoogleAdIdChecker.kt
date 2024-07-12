package com.mq.sls.tracker.identifier

import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.mq.sls.tracker.SLSReporter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GoogleAdIdChecker : IdChecker {

    private var hasInit = false
    private var isEnable = false

    override fun isEnable(): Boolean {
        if (hasInit) {
            return isEnable
        }
        isEnable = try {
            Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient")
            true
        } catch (e: Exception) {
            false
        }
        return isEnable
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun checkIdentifier(callback: (String) -> Unit) {
        SLSReporter.slsDebugLog("GoogleIdAdId.checkIdentifier() isEnable: ${isEnable()}, called with: callback = $callback")
        if (isEnable) {
            GlobalScope.launch {
                try {
                    val info = AdvertisingIdClient.getAdvertisingIdInfo(SLSReporter.instance.getApp())
                    SLSReporter.slsDebugLog("GoogleIdAdId.checkIdentifier() = $info")
                    callback(info.id ?: "")
                } catch (e: Exception) {
                    SLSReporter.slsDebugLog("GoogleIdAdId.checkIdentifier() = $e")
                    callback("")
                }
            }
        } else {
            callback("")
        }
    }
}