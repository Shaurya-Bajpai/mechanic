package com.example.mechanic.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mechanic.data.model.Mechanic

@Composable
fun MechanicCard(
    mechanic: Mechanic,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = mechanic.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = if (mechanic.isOpen) {
                        "Open"
                    } else {
                        "Closed"
                    },
                    color = if (mechanic.isOpen) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "★")

                Text(
                    text = mechanic.rating.toString(),
                    modifier = Modifier.padding(start = 4.dp)
                )

                Text(
                    text = " • ${mechanic.distance}",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "📍")

                Text(
                    text = mechanic.location,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = mechanic.services.replace(", ", " • "),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}