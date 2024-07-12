package com.mq.sls.tracker

import com.mq.sls.tracker.model.LogConfigItem
import com.mq.sls.tracker.utils.SLSSpUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class LogConfigManager(private val sessionHash: Int) {

    private var eventMap = ConcurrentHashMap<String, LogConfigItem>()

    @OptIn(DelicateCoroutinesApi::class)
    fun initConfig() {
        GlobalScope.launch {
            val config = SLSSpUtils.getConfig()
            try {
                val data = SLSReporter.instance.getInitParam().getLogConfig()
                SLSReporter.slsDebugLog("data: $data" )
                val configList = if (data == null || data.version.isEmpty()) {
                    config
                } else {
                    SLSSpUtils.saveLogConfig(data)
                    data.eventConfigList
                }
                updateEventMap(configList)
            } catch (e: Exception) {
                updateEventMap(config)
            }
        }
    }

    private fun updateEventMap(configList: List<LogConfigItem>) {
        val map = ConcurrentHashMap<String, LogConfigItem>()
        configList.forEach {
            map[it.eventName] = it
        }
        eventMap = map
    }

    fun shouldReport(event: String): Boolean {
        if (eventMap.contains(event)) {
            val item = eventMap[event] ?: return true
            if (!item.needReport(sessionHash)) {
                return false
            }
        }
        return true
    }
}