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
import com.mikes.android_advanced_arquitectures.arq.advanced.basic.event_bus.SportEvent
import com.mikes.android_advanced_arquitectures.sport_app.ui.components.AdEventCard
import com.mikes.android_advanced_arquitectures.sport_app.ui.components.SportEventErrorCard
import com.mikes.android_advanced_arquitectures.sport_app.ui.components.SportEventSuccessCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportEventsScreen(
    modifier: Modifier = Modifier,
    onRefresh: suspend () -> List<SportEvent> = { getResultEventsInRealtime() }
) {

    var events: List<SportEvent> by remember { mutableStateOf( getResultEventsInRealtime()) }
    var isRefreshing: Boolean by remember { mutableStateOf(false) }

    val pullToRefreshState = rememberPullToRefreshState()

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

    // Manejar el refresh
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1500)
            events = onRefresh()
            isRefreshing = false
        }
    }

}

//MOCK DATA
private fun getResultEventsInRealtime() = listOf(
    SportEvent.ResultSuccess(1, "Fútbol", listOf("Italia", "Perú", "Corea del Sur")),
    SportEvent.ResultSuccess(2, "Levantamiento de Pesas", listOf("Mongolia", "Alemania", "Turquía")),
    SportEvent.ResultError(10, "Error de red."),
    SportEvent.ResultSuccess(3, "Gimnasia Rítmica", listOf("Rusia", "USA", "Francia")),
    SportEvent.ResultSuccess(4, "Polo Acuático", listOf("España", "Vietnam", "USA")),
    SportEvent.ResultSuccess(5, "Béisbol", null, true),
    SportEvent.ResultError(20, "Error de permisos."),
    SportEvent.ResultSuccess(6, "Rugby", listOf("Sudáfrica", "Qatar", "Rumanía")),
    SportEvent.ResultSuccess(7, "Tenis", listOf("España", "México", "Colombia"))
)