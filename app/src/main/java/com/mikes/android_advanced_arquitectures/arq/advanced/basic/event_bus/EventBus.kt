package com.mikes.android_advanced_arquitectures.arq.advanced.basic.event_bus

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.coroutines.coroutineContext

//Todo: State flow y live data son muy similares

//Todo: state flow no esta ligado al ciclo de la vista, a diferencia de live.

class EventBus {
    private val _events = MutableSharedFlow<Any>()
    val events: SharedFlow<Any> = _events

    suspend fun publish(event: Any) {
        _events.emit(event)
    }


    //Todo: Que hace inline?
    //Todo: que es una función de alto nivel?
    suspend inline fun <reified T> subscribe(crossinline onEvent: (T) -> Unit) {
        events.filterIsInstance<T>()
            .collectLatest { event ->
                coroutineContext.ensureActive()
                onEvent(event)
            }
    }
}