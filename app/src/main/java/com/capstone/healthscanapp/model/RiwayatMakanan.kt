package com.capstone.healthscanapp.model

import com.google.firebase.Timestamp

data class RiwayatMakanan(
    val label: String = " ",
    val timestamp: Timestamp,
    val nutrition: String = " "
)