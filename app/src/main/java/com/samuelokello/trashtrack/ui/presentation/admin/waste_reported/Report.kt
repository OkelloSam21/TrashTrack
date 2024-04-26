package com.samuelokello.trashtrack.ui.presentation.admin.waste_reported

import com.google.type.Date

data class Report(
    val id: Int,
    val location: String,
    val wasteType: String,
    val date: Date
)