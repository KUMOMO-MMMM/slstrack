package com.mq.sls.log.model

import com.mq.sls.log.identifier.AdjustIdChecker
import com.mq.sls.log.identifier.FirebaseIdChecker
import com.mq.sls.log.identifier.GoogleAdIdChecker
import kotlinx.coroutines.flow.MutableStateFlow

class UpdatableInfo {

    fun updateLocation(location: SimpleLocation) {
        locationFlow.value = location
    }

    internal var locationFlow = MutableStateFlow(SimpleLocation())

    internal var googleAdIdFlow = MutableStateFlow<String?>(null)
    internal var userPseudoIdFlow = MutableStateFlow<String?>(null)
    internal var adjustIdFlow = MutableStateFlow<String?>(null)

    private val googleAdIdChecker = GoogleAdIdChecker()
    private val firebaseAdIdChecker = FirebaseIdChecker()
    private val adjustIdChecker = AdjustIdChecker()

    init {
        firebaseAdIdChecker.checkIdentifier {
            userPseudoIdFlow.value = it
        }
        googleAdIdChecker.checkIdentifier {
            googleAdIdFlow.value = it
        }
        adjustIdChecker.checkIdentifier {
            adjustIdFlow.value = it
        }
    }
}