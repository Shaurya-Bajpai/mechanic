package com.example.mechanic.screens.request

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestServiceScreen(
    services: List<String>,
    onBackClick: () -> Unit,
    onSubmitted: () -> Unit,
    viewModel: RequestServiceViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var serviceDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Request Service") },
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.customerName,
                onValueChange = { viewModel.updateCustomerName(it) },
                label = { Text("Customer Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = uiState.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = uiState.vehicleNumber,
                onValueChange = { viewModel.updateVehicleNumber(it) },
                label = { Text("Vehicle Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable { serviceDropdownExpanded = true }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (uiState.selectedService.isBlank()) {
                                "Select Service"
                            } else {
                                uiState.selectedService
                            },
                            color = if (uiState.selectedService.isBlank()) {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                        Text("▼")
                    }
                }
                DropdownMenu(
                    expanded = serviceDropdownExpanded,
                    onDismissRequest = { serviceDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    services.forEach { service ->
                        DropdownMenuItem(
                            text = { Text(service) },
                            onClick = {
                                viewModel.updateSelectedService(service)
                                serviceDropdownExpanded = false
                            }
                        )
                    }
                }
            }


            OutlinedTextField(
                value = uiState.problemDescription,
                onValueChange = { viewModel.updateProblemDescription(it) },
                label = { Text("Problem Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5
            )
            uiState.errorMessage?.let { message ->
                Text(text = message)
            }

            Button(
                onClick = {
                    viewModel.submitRequest()
                    val currentState = viewModel.uiState.value
                    if (
                        currentState.errorMessage == null &&
                        currentState.customerName.isNotBlank() &&
                        currentState.phoneNumber.isNotBlank() &&
                        currentState.vehicleNumber.isNotBlank() &&
                        currentState.selectedService.isNotBlank() &&
                        currentState.problemDescription.isNotBlank()
                    ) {
                        onSubmitted()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Submit Request")
            }
        }
    }
}