package com.mq.sls.tracker.identifier

interface IdChecker {
    fun isEnable(): Boolean

    fun checkIdentifier(callback: (String) -> Unit): Boolean
}