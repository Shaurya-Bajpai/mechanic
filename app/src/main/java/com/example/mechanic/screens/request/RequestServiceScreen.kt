package com.example.mechanic.screens.request

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var serviceExpanded by remember { mutableStateOf(false) }

    val formValid = uiState.customerName.isNotBlank() &&
            uiState.phoneNumber.isNotBlank() &&
            uiState.vehicleNumber.isNotBlank() &&
            uiState.selectedService.isNotBlank() &&
            uiState.problemDescription.isNotBlank()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Request Service") },
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
                    onClick = {
                        viewModel.submitRequest()
                        if (formValid) onSubmitted()
                    },
                    enabled = formValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Build, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Submit Service Request")
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column {
                Text(
                    "What does your vehicle need?",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "Tell us about yourself and the issue with your vehicle.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            FormSection("Customer Details", "Your contact information") {
                AppTextField(
                    value = uiState.customerName,
                    onValueChange = viewModel::updateCustomerName,
                    label = "Full Name",
                    placeholder = "Enter your name",
                    icon = { Icon(Icons.Default.Person, null) }
                )
                Spacer(Modifier.height(12.dp))
                AppTextField(
                    value = uiState.phoneNumber,
                    onValueChange = viewModel::updatePhoneNumber,
                    label = "Phone Number",
                    placeholder = "Enter your phone number",
                    icon = { Icon(Icons.Default.Phone, null) }
                )
            }

            FormSection("Vehicle Details", "Which vehicle needs attention?") {
                AppTextField(
                    value = uiState.vehicleNumber,
                    onValueChange = viewModel::updateVehicleNumber,
                    label = "Vehicle Number",
                    placeholder = "e.g. UP32 AB 1234",
                    icon = { Icon(Icons.Default.DirectionsCar, null) }
                )
            }

            FormSection("Service Details", "Tell us what your vehicle needs") {
                ExposedDropdownMenuBox(
                    expanded = serviceExpanded,
                    onExpandedChange = { serviceExpanded = !serviceExpanded }
                ) {
                    OutlinedTextField(
                        value = uiState.selectedService,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Service Required") },
                        placeholder = { Text("Select a service") },
                        leadingIcon = { Icon(Icons.Default.Build, null) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(serviceExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )
                    ExposedDropdownMenu(
                        expanded = serviceExpanded,
                        onDismissRequest = { serviceExpanded = false }
                    ) {
                        services.forEach { service ->
                            DropdownMenuItem(
                                text = { Text(service) },
                                onClick = {
                                    viewModel.updateSelectedService(service)
                                    serviceExpanded = false
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Build, null)
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.problemDescription,
                    onValueChange = {
                        if (it.length <= 500) {
                            viewModel.updateProblemDescription(it)
                        }
                    },
                    label = { Text("Describe the Problem") },
                    placeholder = { Text("Example: Engine making unusual noise...") },
                    leadingIcon = { Icon(Icons.Default.Description, null) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 5,
                    maxLines = 7,
                    shape = RoundedCornerShape(14.dp),
                    supportingText = {
                        Text("${uiState.problemDescription.length}/500")
                    }
                )
            }

            uiState.errorMessage?.let { message ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        message,
                        modifier = Modifier.padding(14.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Build,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            "What happens next?",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            "A mechanic will review your request and contact you to confirm the service.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FormSection(
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(3.dp))
        Text(
            subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(10.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                content = content
            )
        }
    }
}

@Composable
private fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: @Composable () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        leadingIcon = icon,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        singleLine = true
    )
}