package com.mikes.android_advanced_arquitectures.arq.advanced.basic.singleton

import com.mikes.android_advanced_arquitectures.arq.advanced.basic.event_bus.EventBus

object Singleton {
    private val eventBusInstance: EventBus by lazy { EventBus() }

    fun eventBusInstance() = eventBusInstance
}