package com.samuelokello.trashtrack.ui.components
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun DatePicker() {
    var date by remember { mutableStateOf("") }
    val context = LocalContext.current
    TextFieldComponent(
        value = date,
        onValueChange = { date = it },
        placeholder = "Select Date"
    ) {
        IconButton(onClick = {
            showDatePicker(context) { selectedDate ->
                date = selectedDate
            }
        }) {
            Icon(Icons.Default.CalendarToday, contentDescription = "Calendar Icon")
        }
    }
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    DatePickerDialog(context, { _, selectedYear, monthOfYear, dayOfMonth ->
        val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$selectedYear"
        onDateSelected(selectedDate)
    }, year, month, day).show()
}