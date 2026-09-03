package com.example.mechanic.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mechanic.data.repository.MechanicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MechanicRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()

    init {
        loadMechanics()
    }

    private fun loadMechanics() {

        viewModelScope.launch {

            _uiState.value = HomeUiState(
                isLoading = true
            )

            try {

                val mechanics = repository.getMechanics()

                _uiState.value = HomeUiState(
                    isLoading = false,
                    mechanics = mechanics
                )

            } catch (e: Exception) {

                _uiState.value = HomeUiState(
                    isLoading = false,
                    errorMessage = e.message
                        ?: "Unable to load mechanics"
                )
            }
        }
    }

    fun retry() {
        loadMechanics()
    }
}