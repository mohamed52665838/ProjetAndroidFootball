package com.example.projetandroid.ui_layer.event

import kotlinx.coroutines.flow.MutableStateFlow

enum class EventBusType {
    ADD_MATCH_EVENT,
    JOIN_MATCH_EVENT,
}


object EventsBus {
    val sharedFlowTrigger = MutableStateFlow<Map<EventBusType, String>?>(null)
    suspend inline fun matchAddedEvent(resourceId: Map<EventBusType, String>) {
        sharedFlowTrigger.emit(resourceId)
    }
}