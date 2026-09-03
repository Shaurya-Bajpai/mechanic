package com.example.mechanic.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mechanic.data.model.Mechanic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MechanicDetailsScreen(
    mechanic: Mechanic,
    onBackClick: () -> Unit,
    onRequestServiceClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Mechanic Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Button(
                    onClick = onRequestServiceClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Build, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Request Service")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(76.dp),
                        shape = RoundedCornerShape(22.dp),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = mechanic.name
                                    .trim()
                                    .split(" ")
                                    .take(2)
                                    .joinToString("") { it.first().uppercase() },
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        mechanic.name,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "★",
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(
                            "${mechanic.rating} Rating",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = if (mechanic.isOpen)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        else
                            MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = if (mechanic.isOpen) "● Open Now" else "● Closed",
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 7.dp
                            ),
                            color = if (mechanic.isOpen)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            DetailSection(
                title = "Location",
                icon = { Icon(Icons.Default.LocationOn, null) }
            ) {
                Text(
                    mechanic.address,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            DetailSection(
                title = "Services",
                icon = { Icon(Icons.Default.Build, null) }
            ) {
                mechanic.services
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .forEach { service ->
                        ServiceItem(service)
                    }
            }

            DetailSection(
                title = "Working Hours",
                icon = { Icon(Icons.Default.Schedule, null) }
            ) {
                Text(
                    mechanic.workingHours,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            DetailSection(
                title = "Contact",
                icon = { Icon(Icons.Default.Call, null) }
            ) {
                Text(
                    mechanic.phone,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Build,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            "Need your vehicle serviced?",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            "Send a service request to ${mechanic.name}.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    icon: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    icon()
                    Spacer(Modifier.width(10.dp))
                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(Modifier.height(12.dp))
                content()
            }
        )
    }
}

@Composable
private fun ServiceItem(service: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "✓",
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(10.dp))
        Text(
            service,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}