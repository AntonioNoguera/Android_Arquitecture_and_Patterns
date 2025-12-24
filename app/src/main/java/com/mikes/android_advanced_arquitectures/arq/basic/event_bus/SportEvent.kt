package com.mikes.android_advanced_arquitectures.arq.basic.event_bus

//Todo: Cual es la justificación de Sealed?
sealed class SportEvent {
    data class ResultSuccess(
        val sportKey: Int,
        val sportName: String,
        val results: List<String>?,
        val isWarning: Boolean = false,
    ) : SportEvent()

    data class ResultError(
        val code: Int,
        val msg: String,
    ): SportEvent()

    data object AdEvent: SportEvent()
}