package co.edu.udea.compumovil.gr05_20262.lab1

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
=======
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
<<<<<<< HEAD
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
=======
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
<<<<<<< HEAD
=======
import androidx.compose.runtime.remember
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr05_20262.lab1.model.ColombianCities
import co.edu.udea.compumovil.gr05_20262.lab1.model.ContactData
import co.edu.udea.compumovil.gr05_20262.lab1.model.LatinAmericaCountries
import co.edu.udea.compumovil.gr05_20262.lab1.ui.components.AutocompleteField
import co.edu.udea.compumovil.gr05_20262.lab1.ui.components.SectionTitle
<<<<<<< HEAD
import co.edu.udea.compumovil.gr05_20262.lab1.ui.components.StepProgress
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.LabsTheme
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.Shapes
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.Spacing
=======
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.LabsTheme
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26

class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            LabsTheme {
                Scaffold(
                    topBar = { ContactTopBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ContactDataScreen(
                        modifier = Modifier.padding(innerPadding),
                        onSubmit = { data ->
                            Log.d(
                                getString(R.string.log_tag),
                                data.toLog(this)
                            )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.title_contact_data)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
<<<<<<< HEAD
private fun CardSectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = Spacing.sm)
    )
}

@Composable
=======
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
fun ContactDataScreen(
    modifier: Modifier = Modifier,
    onSubmit: (ContactData) -> Unit
) {
    var phone by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var country by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }

    var phoneError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var countryError by rememberSaveable { mutableStateOf(false) }

    var showDialog by rememberSaveable { mutableStateOf(false) }

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
        SectionTitle(text = stringResource(id = R.string.title_contact_data))

<<<<<<< HEAD
        StepProgress(
            currentStep = 2,
            totalSteps = 2,
            modifier = Modifier.padding(bottom = Spacing.lg)
        )

        // Card 1 — cómo contactar a la persona.
        Card(
            shape = RoundedCornerShape(Shapes.cardCorner),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(Spacing.md)) {
                CardSectionLabel(stringResource(id = R.string.section_contact_data))

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        if (it.isNotBlank()) phoneError = false
                    },
                    label = { Text(text = stringResource(id = R.string.lbl_phone)) },
                    singleLine = true,
                    isError = phoneError,
                    supportingText = if (phoneError) {
                        { Text(text = stringResource(id = R.string.error_invalid_phone)) }
                    } else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(text = stringResource(id = R.string.lbl_address)) },
                    singleLine = true,
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
                    value = email,
                    onValueChange = {
                        email = it
                        if (it.isNotBlank()) emailError = false
                    },
                    label = { Text(text = stringResource(id = R.string.lbl_email)) },
                    singleLine = true,
                    isError = emailError,
                    supportingText = if (emailError) {
                        { Text(text = stringResource(id = R.string.error_invalid_email)) }
                    } else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        // Card 2 — ubicación, agrupada aparte porque usa un componente distinto
        // (autocompletar) y conceptualmente es otro tipo de dato.
        Card(
            shape = RoundedCornerShape(Shapes.cardCorner),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(Spacing.md)) {
                CardSectionLabel(stringResource(id = R.string.section_location_data))

                AutocompleteField(
                    labelRes = R.string.lbl_country,
                    placeholderRes = R.string.hint_search_country,
                    options = LatinAmericaCountries.all,
                    value = country,
                    onValueChange = {
                        country = it
                        if (it.isNotBlank()) countryError = false
                    },
                    errorMessage = if (countryError) stringResource(id = R.string.error_no_country) else null
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                AutocompleteField(
                    labelRes = R.string.lbl_city,
                    placeholderRes = R.string.hint_search_city,
                    options = ColombianCities.all,
                    value = city,
                    onValueChange = { city = it },
                    imeAction = ImeAction.Done
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.lg))
=======
        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                if (it.isNotBlank()) phoneError = false
            },
            label = { Text(text = stringResource(id = R.string.lbl_phone)) },
            singleLine = true,
            isError = phoneError,
            supportingText = if (phoneError) {
                { Text(text = stringResource(id = R.string.error_invalid_phone)) }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text(text = stringResource(id = R.string.lbl_address)) },
            singleLine = true,
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
            value = email,
            onValueChange = {
                email = it
                if (it.isNotBlank()) emailError = false
            },
            label = { Text(text = stringResource(id = R.string.lbl_email)) },
            singleLine = true,
            isError = emailError,
            supportingText = if (emailError) {
                { Text(text = stringResource(id = R.string.error_invalid_email)) }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        AutocompleteField(
            labelRes = R.string.lbl_country,
            placeholderRes = R.string.hint_search_country,
            options = LatinAmericaCountries.all,
            value = country,
            onValueChange = {
                country = it
                if (it.isNotBlank()) countryError = false
            },
            errorMessage = if (countryError) stringResource(id = R.string.error_no_country) else null
        )

        Spacer(modifier = Modifier.height(8.dp))

        AutocompleteField(
            labelRes = R.string.lbl_city,
            placeholderRes = R.string.hint_search_city,
            options = ColombianCities.all,
            value = city,
            onValueChange = { city = it },
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(24.dp))
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26

        Button(
            onClick = {
                phoneError = !ContactData.isPhoneValid(phone)
                emailError = !ContactData.isEmailValid(email)
                countryError = country.isBlank()
                if (phoneError || emailError || countryError) return@Button

                val data = ContactData(
                    phone = phone.trim(),
                    address = address.trim(),
                    email = email.trim(),
                    country = country.trim(),
                    city = city.trim()
                )
                onSubmit(data)
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.action_submit))
        }

<<<<<<< HEAD
        Spacer(modifier = Modifier.height(Spacing.md))
=======
        Spacer(modifier = Modifier.height(16.dp))
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.confirm_title)) },
            text = { Text(text = stringResource(id = R.string.confirm_message)) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = R.string.confirm_button))
                }
            }
        )
    }
}
