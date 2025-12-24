package com.mikes.android_advanced_arquitectures.sport_app.ui.screens

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.mikes.android_advanced_arquitectures.arq.basic.event_bus.EventBus
import com.mikes.android_advanced_arquitectures.arq.basic.event_bus.SportEvent
import com.mikes.android_advanced_arquitectures.sport_app.ui.components.AdEventCard
import com.mikes.android_advanced_arquitectures.sport_app.ui.components.SportEventErrorCard
import com.mikes.android_advanced_arquitectures.sport_app.ui.components.SportEventSuccessCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportEventsScreen(
    modifier: Modifier = Modifier,
    eventBus: EventBus, // Inyectar el EventBus
    onRefresh: suspend () -> List<SportEvent> = { emptyList() }
) {
    // Estado para mantener la lista de eventos
    //var events by remember { mutableStateOf<List<SportEvent>>(emptyList()) } // Es una lista
    val events = remember { mutableStateListOf<SportEvent>() }

    var isRefreshing by remember { mutableStateOf(false) }

    //??
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    // Suscribirse al EventBus cuando el composable se monta
    LaunchedEffect(eventBus) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            eventBus.subscribe<SportEvent> { event ->
                events.add(0,event)
            }
        }
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            val newEvents = onRefresh() // Llamar la función de refresh
            events.clear()
            events.addAll(newEvents)
            isRefreshing = false // Terminar el refresh
        }
    }

    //Qué es launched effect?
    //Pending Refresh Logic
    Scaffold( // Que pija es el scaffold
        topBar = {
            TopAppBar(
                title = { Text("Eventos Deportivos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        },
        content = { paddingValues -> // contexto de esto?
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    isRefreshing = true
                },
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn (
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp), // esto del padding values?
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = events,
                        key = { event ->
                            when (event) {
                                is SportEvent.ResultSuccess -> event.sportKey
                                is SportEvent.ResultError -> "error_${event.code}"
                                is SportEvent.AdEvent -> "ad_event"
                            }
                        }
                    ) { event ->
                        when (event) {
                            is SportEvent.ResultSuccess -> {
                                SportEventSuccessCard(event = event)
                            }

                            is SportEvent.ResultError -> {
                                SportEventErrorCard(event = event)
                            }

                            is SportEvent.AdEvent -> {
                                AdEventCard()
                            }
                        }
                    }
                }
            }
        }
    )
}
