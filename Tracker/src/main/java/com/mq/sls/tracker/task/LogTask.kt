package com.mq.sls.tracker.task

interface LogTask {

    fun initTask()

    fun getDelayDuration(): Long = 0L

    fun release()
}