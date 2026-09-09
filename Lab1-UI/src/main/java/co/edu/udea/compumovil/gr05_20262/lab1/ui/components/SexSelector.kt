package co.edu.udea.compumovil.gr05_20262.lab1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr05_20262.lab1.model.Sex

@Composable
fun SexSelector(
    selected: Sex?,
    onSelect: (Sex) -> Unit,
    modifier: Modifier = Modifier,
    horizontal: Boolean = false,
    centered: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start
    ) {
        Text(
            text = stringResource(id = co.edu.udea.compumovil.gr05_20262.lab1.R.string.lbl_sex),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        if (horizontal) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Sex.entries.forEach { sex ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (sex == selected),
                                onClick = { onSelect(sex) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = (sex == selected), onClick = null)
                        Text(
                            text = stringResource(id = sex.labelRes),
                            modifier = Modifier.padding(start = 4.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        } else {
            Sex.entries.forEach { sex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (sex == selected),
                            onClick = { onSelect(sex) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = (sex == selected), onClick = null)
                    Text(
                        text = stringResource(id = sex.labelRes),
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
