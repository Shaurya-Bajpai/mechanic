package com.example.mechanic.screens.details

import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.mechanic.data.model.Mechanic
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MechanicDetailsScreen(
    mechanic: Mechanic,
    onBackClick: () -> Unit,
    onRequestServiceClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Mechanic Details")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("←", modifier = Modifier.padding(4.dp))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = mechanic.name, style = MaterialTheme.typography.headlineMedium)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Star emoji as rating icon to avoid material icon imports
                Text("★")
                Text(
                    text = "${mechanic.rating} Rating",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📍")
                Text(text = mechanic.address, modifier = Modifier.padding(start = 8.dp))
            }

            Text(text = "Services", style = MaterialTheme.typography.titleLarge)
            Text(text = mechanic.services.replace(", ", "\n"))

            Text(text = "Working Hours", style = MaterialTheme.typography.titleLarge)
            Text(text = mechanic.workingHours)

            Text(text = "Phone", style = MaterialTheme.typography.titleLarge)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📞")
                Text(text = mechanic.phone, modifier = Modifier.padding(start = 8.dp))
            }

            Text(
                text = if (mechanic.isOpen) {
                    "Currently Open"
                } else {
                    "Currently Closed"
                },
                color = if (mechanic.isOpen) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onRequestServiceClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Request Service")
            }
        }
    }
}