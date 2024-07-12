package com.mq.sls.demo

import android.app.Application
import android.util.Log
import com.mq.sls.log.SLSReporter
import com.mq.sls.log.model.LogConfig
import java.util.UUID

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initSLS()
    }

    private fun initSLS() {
        SLSReporter
            .build(this) {
                isDebug = true
                appName = "TrackerDemo"
                flavor = "demo"
                pingHost = "baidu.com"
                getLogin = { "12345678" }
                getDeviceId = { UUID.randomUUID().toString() }
                getAdjustFrom = { "testAdjust" }
                getServerTime = { System.currentTimeMillis() }
                errorCallback = { id, args -> Log.d("SLSLog", "id: $id, args: $args") }
                getLocalLanguage = { "CN" }
                getLogConfig = suspend { LogConfig() }
                getSLSLogConfig = suspend { null }
            }
            .init()
    }
}