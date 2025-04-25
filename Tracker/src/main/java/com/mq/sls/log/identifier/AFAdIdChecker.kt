package com.mq.sls.log.identifier

import com.appsflyer.AppsFlyerLib
import com.mq.sls.log.SLSReporter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AFAdIdChecker: BaseIdChecker() {

    override fun checkEnable(): Boolean {
        return try {
            Class.forName("com.appsflyer.AppsFlyerLib")
            true
        } catch (e: Exception) {
            false
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun checkIdentifier(callback: (String) -> Unit): Boolean {
        SLSReporter.slsDebugLog("AFAdIdChecker.checkIdentifier() isEnable: ${isEnable()}")
        if (super.checkIdentifier(callback))
            return true
        GlobalScope.launch(Dispatchers.IO) {
            val afAdId = AppsFlyerLib.getInstance().getAppsFlyerUID(SLSReporter.instance.getApp())
            callback(afAdId ?: "")
            SLSReporter.slsDebugLog("AppsFlyerLib.afAdId = $afAdId")
        }
        return true
    }
}