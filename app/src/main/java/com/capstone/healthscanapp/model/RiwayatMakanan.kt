package com.capstone.healthscanapp.model

import com.google.firebase.Timestamp

data class RiwayatMakanan(
    val label: String = "",
    val timestamp: Timestamp? = null, // Change the data type to Timestamp
    val nutrition: String = ""
) {
    // No-argument constructor required by Firestore
    constructor() : this("", null, "")
}
