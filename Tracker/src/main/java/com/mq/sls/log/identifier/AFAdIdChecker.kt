package com.mq.sls.log.identifier

import com.appsflyer.AppsFlyerLib
import com.mq.sls.log.SLSReporter

class AFAdIdChecker: BaseIdChecker() {

    override fun checkEnable(): Boolean {
        return try {
            Class.forName("com.appsflyer.AppsFlyerLib")
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun checkIdentifier(callback: (String) -> Unit): Boolean {
        SLSReporter.slsDebugLog("AdjustId.checkIdentifier() isEnable: ${isEnable()}")
        if (super.checkIdentifier(callback))
            return true
        val afAdId = AppsFlyerLib.getInstance().getAppsFlyerUID(SLSReporter.instance.getApp())
        callback(afAdId ?: "")
        SLSReporter.slsDebugLog("AppsFlyerLib.afAdId = $afAdId")
        return true
    }
}