package co.edu.udea.compumovil.gr05_20262.lab1.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import co.edu.udea.compumovil.gr05_20262.lab1.R
import co.edu.udea.compumovil.gr05_20262.lab1.model.PersonalData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    value: Long?,
    onValueChange: (Long) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val context = LocalContext.current
    var open by remember { mutableStateOf(false) }

    val displayed = value?.let { PersonalData.formatDate(context, it) }
        ?: stringResource(id = R.string.date_placeholder)

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = displayed,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = stringResource(id = R.string.lbl_birthdate)) },
            placeholder = { Text(text = stringResource(id = R.string.hint_select_date)) },
            isError = isError,
            supportingText = errorMessage?.let { { Text(text = it) } },
            trailingIcon = {
                IconButton(onClick = { open = true }) {
                    Icon(imageVector = Icons.Filled.CalendarToday, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (open) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = value)
        DatePickerDialog(
            onDismissRequest = { open = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { onValueChange(it) }
                    open = false
                }) { Text(text = stringResource(id = R.string.action_ok)) }
            },
            dismissButton = {
                TextButton(onClick = { open = false }) {
                    Text(text = stringResource(id = R.string.action_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
