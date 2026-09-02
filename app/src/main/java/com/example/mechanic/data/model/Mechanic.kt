package com.example.mechanic.data.model

data class Mechanic(
    val id: Int,
    val name: String,
    val rating: Double,
    val distance: String,
    val location: String,
    val address: String,
    val services: List<String>,
    val isOpen: Boolean,
    val workingHours: String,
    val phone: String
)