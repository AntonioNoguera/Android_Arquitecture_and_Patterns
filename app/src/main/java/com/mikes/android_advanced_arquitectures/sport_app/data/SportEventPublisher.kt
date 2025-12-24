package com.mikes.android_advanced_arquitectures.sport_app.data


import com.mikes.android_advanced_arquitectures.arq.basic.event_bus.EventBus
import com.mikes.android_advanced_arquitectures.arq.basic.event_bus.SportEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SportEventPublisher(
    private val eventBus: EventBus,
    private val scope: CoroutineScope
) {
    private var isPublishing = false

    fun startPublishing() {
        if (isPublishing) return
        isPublishing = true

        scope.launch {
            val events = getResultEventsInRealtime()
            events.forEach { event ->
                delay(Random.nextLong(0,1000))
                eventBus.publish(event)
            }
            isPublishing = false
        }
    }

    private fun getResultEventsInRealtime() = listOf(
        SportEvent.ResultSuccess(1, "Fútbol", listOf("Italia", "Perú", "Corea del Sur")),
        SportEvent.ResultSuccess(2, "Levantamiento de Pesas", listOf("Mongolia", "Alemania", "Turquía")),
        SportEvent.ResultError(10, "Error de red."),
        SportEvent.ResultSuccess(3, "Gimnasia Rítmica", listOf("Rusia", "USA", "Francia")),
        SportEvent.ResultSuccess(4, "Polo Acuático", listOf("España", "Vietnam", "USA")),
        SportEvent.ResultSuccess(5, "Béisbol", null, true),
        SportEvent.ResultError(20, "Error de permisos."),
        SportEvent.AdEvent,
        SportEvent.ResultSuccess(6, "Rugby", listOf("Sudáfrica", "Qatar", "Rumanía")),
        SportEvent.ResultSuccess(7, "Tenis", listOf("España", "México", "Colombia"))
    )
}