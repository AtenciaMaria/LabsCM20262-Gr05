package co.edu.udea.compumovil.gr05_20262.lab1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
<<<<<<< HEAD
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
=======
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr05_20262.lab1.model.EducationLevel
import co.edu.udea.compumovil.gr05_20262.lab1.model.PersonalData
import co.edu.udea.compumovil.gr05_20262.lab1.model.Sex
import co.edu.udea.compumovil.gr05_20262.lab1.model.label
import co.edu.udea.compumovil.gr05_20262.lab1.ui.components.DatePickerField
import co.edu.udea.compumovil.gr05_20262.lab1.ui.components.EducationDropdown
import co.edu.udea.compumovil.gr05_20262.lab1.ui.components.SectionTitle
import co.edu.udea.compumovil.gr05_20262.lab1.ui.components.SexSelector
<<<<<<< HEAD
import co.edu.udea.compumovil.gr05_20262.lab1.ui.components.StepProgress
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.LabsTheme
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.Shapes
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.Spacing
=======
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.LabsTheme
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26

class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            LabsTheme {
                Scaffold(
                    topBar = { PersonalTopBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    PersonalDataScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNext = { data ->
                            Log.d(
                                getString(R.string.log_tag),
                                data.toLog(this)
                            )
                            startActivity(Intent(this, ContactDataActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonalTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.title_personal_data)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

<<<<<<< HEAD
/** Título pequeño reutilizado como encabezado dentro de cada Card. */
@Composable
private fun CardSectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = Spacing.sm)
    )
}

=======
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
@Composable
fun PersonalDataScreen(
    modifier: Modifier = Modifier,
    onNext: (PersonalData) -> Unit
) {
    var names by rememberSaveable { mutableStateOf("") }
    var surnames by rememberSaveable { mutableStateOf("") }
    var sexKey by rememberSaveable { mutableStateOf<String?>(null) }
    var birthDateMillis by rememberSaveable { mutableStateOf<Long?>(null) }
    var educationKey by rememberSaveable { mutableStateOf<String?>(null) }

    var namesError by rememberSaveable { mutableStateOf(false) }
    var surnamesError by rememberSaveable { mutableStateOf(false) }
    var birthDateError by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .navigationBarsPadding()
<<<<<<< HEAD
            .padding(horizontal = Spacing.md, vertical = Spacing.md),
=======
            .padding(horizontal = 16.dp, vertical = 16.dp),
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
        verticalArrangement = Arrangement.Top
    ) {
        SectionTitle(text = stringResource(id = R.string.title_personal_data))

<<<<<<< HEAD
        StepProgress(
            currentStep = 1,
            totalSteps = 2,
            modifier = Modifier.padding(bottom = Spacing.lg)
        )

        // Card 1 — lo esencial para identificar a la persona.
        Card(
            shape = RoundedCornerShape(Shapes.cardCorner),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(Spacing.md)) {
                CardSectionLabel(stringResource(id = R.string.section_basic_data))

                OutlinedTextField(
                    value = names,
                    onValueChange = {
                        names = it
                        if (it.isNotBlank()) namesError = false
                    },
                    label = { Text(text = stringResource(id = R.string.lbl_names)) },
                    singleLine = true,
                    isError = namesError,
                    supportingText = if (namesError) {
                        { Text(text = stringResource(id = R.string.error_required)) }
                    } else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrectEnabled = false,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                OutlinedTextField(
                    value = surnames,
                    onValueChange = {
                        surnames = it
                        if (it.isNotBlank()) surnamesError = false
                    },
                    label = { Text(text = stringResource(id = R.string.lbl_surnames)) },
                    singleLine = true,
                    isError = surnamesError,
                    supportingText = if (surnamesError) {
                        { Text(text = stringResource(id = R.string.error_required)) }
                    } else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrectEnabled = false,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Spacing.md))

                SexSelector(
                    selected = Sex.fromKey(sexKey),
                    onSelect = { sexKey = it.name }
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        // Card 2 — datos que se usan con menos frecuencia en el resto del flujo.
        Card(
            shape = RoundedCornerShape(Shapes.cardCorner),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(Spacing.md)) {
                CardSectionLabel(stringResource(id = R.string.section_additional_data))

                DatePickerField(
                    value = birthDateMillis,
                    onValueChange = {
                        birthDateMillis = it
                        if (it != null) birthDateError = false
                    },
                    isError = birthDateError,
                    errorMessage = if (birthDateError) stringResource(id = R.string.error_required) else null
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                EducationDropdown(
                    selected = EducationLevel.fromKey(educationKey),
                    onSelect = { educationKey = it.name }
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.lg))
=======
        OutlinedTextField(
            value = names,
            onValueChange = {
                names = it
                if (it.isNotBlank()) namesError = false
            },
            label = { Text(text = stringResource(id = R.string.lbl_names)) },
            singleLine = true,
            isError = namesError,
            supportingText = if (namesError) {
                { Text(text = stringResource(id = R.string.error_required)) }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = surnames,
            onValueChange = {
                surnames = it
                if (it.isNotBlank()) surnamesError = false
            },
            label = { Text(text = stringResource(id = R.string.lbl_surnames)) },
            singleLine = true,
            isError = surnamesError,
            supportingText = if (surnamesError) {
                { Text(text = stringResource(id = R.string.error_required)) }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        SexSelector(
            selected = Sex.fromKey(sexKey),
            onSelect = { sexKey = it.name }
        )

        Spacer(modifier = Modifier.height(8.dp))

        DatePickerField(
            value = birthDateMillis,
            onValueChange = {
                birthDateMillis = it
                if (it != null) birthDateError = false
            },
            isError = birthDateError,
            errorMessage = if (birthDateError) stringResource(id = R.string.error_required) else null
        )

        Spacer(modifier = Modifier.height(8.dp))

        EducationDropdown(
            selected = EducationLevel.fromKey(educationKey),
            onSelect = { educationKey = it.name }
        )

        Spacer(modifier = Modifier.height(24.dp))
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26

        Button(
            onClick = {
                namesError = names.isBlank()
                surnamesError = surnames.isBlank()
                birthDateError = birthDateMillis == null
                if (namesError || surnamesError || birthDateError) return@Button

                val data = PersonalData(
                    names = names,
                    surnames = surnames,
                    sex = Sex.fromKey(sexKey),
                    birthDateMillis = birthDateMillis,
                    education = EducationLevel.fromKey(educationKey)
                )
                onNext(data)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.action_next))
        }

<<<<<<< HEAD
        Spacer(modifier = Modifier.height(Spacing.md))
=======
        Spacer(modifier = Modifier.height(16.dp))
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
    }
}
