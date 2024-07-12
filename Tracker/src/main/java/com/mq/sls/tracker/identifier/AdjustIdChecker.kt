package com.mq.sls.tracker.identifier

import com.adjust.sdk.Adjust
import com.mq.sls.tracker.SLSReporter

class AdjustIdChecker : IdChecker {

    private var hasInit = false
    private var enable = false

    override fun isEnable(): Boolean {
        if (hasInit) {
            return enable
        }
        enable = try {
            Class.forName("com.adjust.sdk.Adjust")
            true
        } catch (e: Exception) {
            false
        }
        return enable
    }

    override fun checkIdentifier(callback: (String) -> Unit) {
        SLSReporter.slsDebugLog("AdjustId.checkIdentifier() isEnable: ${isEnable()}, called with: callback = $callback")
        callback(if (isEnable()) Adjust.getAdid() ?: "" else "")
        SLSReporter.slsDebugLog("AdjustId.checkIdentifier() = ${Adjust.getAdid()}")
    }
}