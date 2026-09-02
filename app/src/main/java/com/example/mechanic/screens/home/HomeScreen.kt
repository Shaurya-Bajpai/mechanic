package com.example.mechanic.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mechanic.screens.components.ErrorView
import com.example.mechanic.screens.components.LoadingView
import com.example.mechanic.screens.components.MechanicCard

@Composable
fun HomeScreen(
    onMechanicClick: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    var searchQuery by remember {
        mutableStateOf("")
    }

    val filteredMechanics = uiState.mechanics.filter { mechanic ->

        mechanic.name.contains(
            searchQuery,
            ignoreCase = true
        ) ||

                mechanic.location.contains(
                    searchQuery,
                    ignoreCase = true
                ) ||

                mechanic.services.any { service ->
                    service.contains(
                        searchQuery,
                        ignoreCase = true
                    )
                }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Find a Mechanic",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            label = {
                Text("Search mechanics")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        when {

            uiState.isLoading -> {
                LoadingView()
            }

            uiState.errorMessage != null -> {
                ErrorView(
                    message = uiState.errorMessage!!,
                    onRetry = {
                        viewModel.retry()
                    }
                )
            }

            else -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        items = filteredMechanics,
                        key = {
                            it.id
                        }
                    ) { mechanic ->

                        MechanicCard(
                            mechanic = mechanic,
                            onClick = {
                                onMechanicClick(mechanic.id)
                            }
                        )
                    }
                }
            }
        }
    }
}