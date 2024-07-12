package com.mq.sls.tracker.identifier

import com.adjust.sdk.Adjust
import com.mq.sls.tracker.SLSReporter

class AdjustIdChecker : BaseIdChecker() {

    override fun checkEnable(): Boolean {
        return try {
            Class.forName("com.adjust.sdk.Adjust")
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun checkIdentifier(callback: (String) -> Unit): Boolean {
        SLSReporter.slsDebugLog("AdjustId.checkIdentifier() isEnable: ${isEnable()}")
        if (super.checkIdentifier(callback))
            return true
        val adId = Adjust.getAdid()
        callback(adId ?: "")
        SLSReporter.slsDebugLog("AdjustId.adId = $adId")
        return true
    }
}