package com.samuelokello.trashtrack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.libraries.places.api.model.AutocompletePrediction

@Composable
fun LocationAutocompleteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    suggestions: List<AutocompletePrediction>,
    onSuggestionSelected: (AutocompletePrediction) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    val showDropdown = remember { mutableStateOf(true) }
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showDropdown.value = true
                            },
            placeholder = { Text(text = placeholder) },
            trailingIcon = {
                           Icons.Default.LocationOn
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        DropdownMenu(
            expanded = showDropdown.value,
            onDismissRequest = { showDropdown.value = false }
        ) {
            suggestions.forEach { prediction ->
                DropdownMenuItem(onClick = {
                    onSuggestionSelected(prediction)
                    showDropdown.value = false
                },
                    text = { Text(prediction.getFullText(null).toString()) }
                )
            }
        }
    }
}
