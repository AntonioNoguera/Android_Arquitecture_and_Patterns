package com.mikes.android_advanced_arquitectures.arq.advanced.basic.event_bus

import com.mikes.android_advanced_arquitectures.arq.advanced.basic.getAdEventsInRealtime
import com.mikes.android_advanced_arquitectures.arq.advanced.basic.getEventsInRealtime
import com.mikes.android_advanced_arquitectures.arq.advanced.basic.getResultEventsInRealtime
import com.mikes.android_advanced_arquitectures.arq.advanced.basic.singleton.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

private lateinit var eventBus: EventBus
private val job = Job()
private val scope = CoroutineScope(Dispatchers.IO + job)

fun main() {
    initEventBus()

    runBlocking {
//        Previous Demo with class instances
//        setupSubscriber(scope)
//        setupSubscriberTwo(scope)
//        setupPublisher()

        setUpSubscriberResult(scope)
        setUpSubscriberError(scope)
        setUpSubscriberAnalytics(scope)
        setupPublishers()
    }
}

fun setUpSubscriberResult(scope: CoroutineScope) {
    scope.launch {
        eventBus.subscribe<SportEvent.ResultSuccess> { eventBus ->
            println("Result: ${eventBus.sportName}")
        }
    }
}
fun setUpSubscriberError(scope: CoroutineScope) {
    scope.launch {
        eventBus.subscribe<SportEvent.ResultError> { eventBus ->
            println("Error Code: ${eventBus.code}, Message: ${eventBus.msg}")
        }
    }
}

fun setUpSubscriberAnalytics(scope: CoroutineScope) {
    scope.launch {
        eventBus.subscribe<SportEvent.AdEvent> { eventBus ->
            println("Ad Click. Send data to server... ${eventBus}")
        }
    }
}

suspend fun setupPublishers() {
    // Es necesario que este se lance al inicio para crear un nuevo hilo desde el scope ?
    scope.launch {
        getAdEventsInRealtime().forEach {
            delay(someTime())
            eventBus.publish(it)
        }
    }

    getResultEventsInRealtime().forEach {
        delay(someTime())
        eventBus.publish(it)
    }
}

fun initEventBus() {
    eventBus = Singleton.eventBusInstance() //EventBus()
}

suspend fun setupSubscriber(coroutineScope: CoroutineScope) {
    coroutineScope.launch{
        eventBus.subscribe<Result> { result ->
            println(result.sportName)
        }
    }
}

suspend fun setupSubscriberTwo(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        eventBus.subscribe<Result> { result ->
            if (result.isWarning) {
                println("Warning: ${result.sportName}")
            }
        }
    }
}


suspend fun setupPublisher() {
    getEventsInRealtime().forEach {
        delay(someTime())
        eventBus.publish(it)
    }
}

fun someTime(): Long = Random.nextLong(500, 2_000)