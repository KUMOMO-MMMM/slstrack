package com.mq.sls.tracker.model

import androidx.annotation.Keep

@Keep
data class SimpleLocation(
    var longitude: String = "denied",
    var latitude: String = "denied",
)