package com.example.projetandroid.ui_layer.event

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

enum class EventBusType {
    ADD_MATCH_EVENT,
    JOIN_MATCH_EVENT,
}


object EventsBus {
    val _sharedFlowTrigger = MutableStateFlow<Map<EventBusType, Any>?>(null)

    val sharedFlowTrigger: SharedFlow<Map<EventBusType, Any>?> = _sharedFlowTrigger
    suspend inline fun <T : Any> matchAddedEvent(resourceId: Map<EventBusType, T>) {
        println("value emitted ${resourceId.values}")
        _sharedFlowTrigger.emit(resourceId)
        println("Clean up")
        _sharedFlowTrigger.emit(null)
    }//
}