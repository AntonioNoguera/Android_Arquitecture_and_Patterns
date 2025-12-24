package com.mikes.android_advanced_arquitectures.sport_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mikes.android_advanced_arquitectures.arq.basic.event_bus.SportEvent
import com.mikes.android_advanced_arquitectures.sport_app.utils.getSportImageUrl

@Composable
fun SportEventSuccessCard(
    event: SportEvent.ResultSuccess,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (event.isWarning) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) { // Como xp de desarrollo es molesto que estoy definiendo el componente y me marca como error automáticamente algo cuando apenas le estoy definiendo valor
            AsyncImage(
                model = getSportImageUrl(event.sportKey),
                contentDescription = event.sportName,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                ,
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = event.sportName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )

                    if (event.isWarning) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Advertencia",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }

                if (event.results != null) {
                    Text(
                        text = "Resultados: ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    event.results.forEachIndexed { index, country ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            //Todo: add THE component
                            MedalBadge(
                                position = index + 1,
                            )
                            Text(
                                text = country,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Sin resultados disponibles",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic // Todo; Check if I can define this myself
                    )
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun SportEventSuccessCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SportEventSuccessCard(
                event = SportEvent.ResultSuccess(
                    sportKey = 1,
                    sportName = "Fútbol",
                    results = listOf("Italia", "Perú", "Corea del Sur")
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SportEventSuccessCardWithWarningPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SportEventSuccessCard(
                event = SportEvent.ResultSuccess(
                    sportKey = 5,
                    sportName = "Béisbol",
                    results = null,
                    isWarning = true
                )
            )
        }
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SportEventSuccessCardDarkPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SportEventSuccessCard(
                event = SportEvent.ResultSuccess(
                    sportKey = 7,
                    sportName = "Tenis",
                    results = listOf("España", "México", "Colombia")
                )
            )
        }
    }
}