package com.mq.sls.log.utils

import android.webkit.JavascriptInterface
import androidx.annotation.Keep
import com.mq.sls.log.SLSReporter

@Keep
class SLSJSBridge {
    @JavascriptInterface
    fun report(key: String, content: String) {
        try {
            val map = content.parseJSON2Map()
            SLSReporter.report(key, map)
        } catch (e: Exception) {
            // ignore
        }
    }
}