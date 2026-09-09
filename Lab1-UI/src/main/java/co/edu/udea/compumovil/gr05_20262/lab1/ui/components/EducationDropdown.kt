package co.edu.udea.compumovil.gr05_20262.lab1.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.edu.udea.compumovil.gr05_20262.lab1.R
import co.edu.udea.compumovil.gr05_20262.lab1.model.EducationLevel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationDropdown(
    selected: EducationLevel?,
    onSelect: (EducationLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected?.let { stringResource(it.labelRes) } ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(text = stringResource(id = R.string.lbl_education)) },
            placeholder = { Text(text = stringResource(id = R.string.hint_select)) },
            trailingIcon = {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            EducationLevel.entries.forEach { level ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = level.labelRes)) },
                    onClick = {
                        onSelect(level)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
