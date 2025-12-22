package com.mikes.android_advanced_arquitectures.arq.advanced.basic.event_bus

//TODO: Diferencia entre clase y data class de kotlin?

data class Result(
    val sportKey: Int,
    val sportName: String,
    val result: List<String>?,
    val isWarning: Boolean = false,
) {

}