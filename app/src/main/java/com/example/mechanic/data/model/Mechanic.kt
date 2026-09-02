package com.example.mechanic.data.model

data class Mechanic(
    val id: String,
    val name: String,
    val rating: Double,
    val distance: String,
    val location: String,
    val address: String,
    val services: String,
    val isOpen: Boolean,
    val workingHours: String,
    val phone: String
)