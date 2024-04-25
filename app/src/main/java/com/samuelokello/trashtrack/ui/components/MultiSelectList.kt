package com.samuelokello.trashtrack.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MultiSelectList(
    items: List<String>,
    selectedItems: MutableState<List<Boolean>>,
    onSelectedChange: (index: Int, isSelected: Boolean) -> Unit
) {
    LazyColumn {
        itemsIndexed(items) { index, item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedItems.value[index],
                    onCheckedChange = { isChecked ->
                        onSelectedChange(index, isChecked)
                    }
                )
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}