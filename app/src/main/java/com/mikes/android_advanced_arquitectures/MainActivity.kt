package com.mikes.android_advanced_arquitectures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.mikes.android_advanced_arquitectures.arq.basic.event_bus.EventBus
import com.mikes.android_advanced_arquitectures.sport_app.data.SportEventPublisher
import com.mikes.android_advanced_arquitectures.sport_app.ui.screens.SportEventsScreen

class MainActivity : ComponentActivity() {


    private val eventBus = EventBus()
    private lateinit var eventPublisher: SportEventPublisher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        eventPublisher = SportEventPublisher(
            eventBus = eventBus,
            scope = lifecycleScope
        )

        setContent {
            SportEventsScreen(
                eventBus = eventBus,
                onRefresh = {
                    eventPublisher.startPublishing()
                    emptyList()
                }
            )

//            Android_Advanced_ArquitecturesTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }

        eventPublisher.startPublishing()
    }
}