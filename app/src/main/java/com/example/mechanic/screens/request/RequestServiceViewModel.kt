package com.example.mechanic.screens.request

import androidx.lifecycle.ViewModel
import com.example.mechanic.data.model.RequestServiceUiState
import com.example.mechanic.data.model.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RequestServiceViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RequestServiceUiState())
    val uiState: StateFlow<RequestServiceUiState> = _uiState.asStateFlow()

    fun updateCustomerName(value: String) {
        _uiState.value = _uiState.value.copy(customerName = value, errorMessage = null)
    }

    fun updatePhoneNumber(value: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = value, errorMessage = null)
    }

    fun updateVehicleNumber(value: String) {
        _uiState.value = _uiState.value.copy(vehicleNumber = value, errorMessage = null)
    }

    fun updateSelectedService(value: String) {
        _uiState.value = _uiState.value.copy(selectedService = value, errorMessage = null)
    }

    fun updateProblemDescription(value: String) {
        _uiState.value = _uiState.value.copy(problemDescription = value, errorMessage = null)
    }

    fun submitRequest() {
        val state = _uiState.value
        when {
            state.customerName.isBlank() -> {
                setError("Please enter your name")
            }
            state.phoneNumber.isBlank() -> {
                setError("Please enter your phone number")
            }

            !state.phoneNumber.matches(Regex("^[0-9]{10}$")) -> {
                setError("Phone number must contain 10 digits")
            }
            state.vehicleNumber.isBlank() -> {
                setError("Please enter your vehicle number")
            }
            state.vehicleNumber.length < 6 -> {
                setError("Please enter a valid vehicle number")
            }
            state.selectedService.isBlank() -> {
                setError("Please select a service")
            }
            state.problemDescription.isBlank() -> {
                setError("Please describe the problem")
            }

            else -> {
                val request = ServiceRequest(
                    customerName = state.customerName,
                    phoneNumber = state.phoneNumber,
                    vehicleNumber = state.vehicleNumber,
                    service = state.selectedService,
                    problemDescription = state.problemDescription
                )

                // For this assignment we only need
                // to demonstrate successful submission.
                // API submission can be added later.

                _uiState.value = state.copy(isSubmitted = true, errorMessage = null)
            }
        }
    }

    private fun setError(message: String) {
        _uiState.value = _uiState.value.copy(errorMessage = message)
    }
}