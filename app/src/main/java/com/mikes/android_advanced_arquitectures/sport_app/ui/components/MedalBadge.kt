package com.mikes.android_advanced_arquitectures.sport_app.ui.components

import android.view.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MedalBadge(
    position: Int,
    modifier: Modifier = Modifier,
) {
    val (emoji, color) = when (position) {
        1 -> "🥇" to Color(0xFFFFD700)
        2 -> "🥈" to Color(0xFFC0C0C0)
        3 -> "🥉" to Color(0xFFCD7F32)
        else -> "$position" to MaterialTheme.colorScheme.primary
    }

    Surface(
        shape = RoundedCornerShape(4.dp),
        color = color.copy(alpha = 0.2f),
        modifier = modifier.size(24.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MedalBadgePreview() {
    MaterialTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            MedalBadge(position = 1)
            MedalBadge(position = 2)
            MedalBadge(position = 3)
            MedalBadge(position = 4)
        }
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MedalBadgeDarkPreview() {
    MaterialTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MedalBadge(position = 1)
            MedalBadge(position = 2)
            MedalBadge(position = 3)
        }
    }
}