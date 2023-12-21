package com.capstone.healthscanapp.model

import java.util.Date

data class RiwayatMakanan(
    val label: String = "",
    val timestamp: Date = Date(),
    val nutrition: String = "",
)
