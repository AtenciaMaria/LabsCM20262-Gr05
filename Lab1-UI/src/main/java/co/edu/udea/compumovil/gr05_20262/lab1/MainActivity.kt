package co.edu.udea.compumovil.gr05_20262.lab1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
<<<<<<< HEAD
import androidx.compose.foundation.layout.Box
=======
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
<<<<<<< HEAD
import androidx.compose.foundation.shape.CircleShape
=======
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
<<<<<<< HEAD
import androidx.compose.material3.Surface
=======
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.LabsTheme
<<<<<<< HEAD
import co.edu.udea.compumovil.gr05_20262.lab1.ui.theme.Spacing
=======
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            LabsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WelcomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        onStart = {
                            startActivity(Intent(this, PersonalDataActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, onStart: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
<<<<<<< HEAD
            .padding(Spacing.lg),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Insignia circular tonal: el ícono ya no "flota" solo sobre el
        // fondo, queda contenido en una forma con el color de marca.
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(120.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Filled.PersonAdd,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(56.dp)
                )
            }
        }
        Text(
            text = stringResource(id = R.string.welcome_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = Spacing.lg)
=======
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.PersonAdd,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = stringResource(id = R.string.welcome_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 16.dp)
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
        )
        Text(
            text = stringResource(id = R.string.welcome_subtitle),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
<<<<<<< HEAD
            modifier = Modifier.padding(top = Spacing.sm),
=======
            modifier = Modifier.padding(top = 8.dp),
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.welcome_description),
            style = MaterialTheme.typography.bodyMedium,
<<<<<<< HEAD
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = Spacing.lg)
=======
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 24.dp)
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
        )
        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
<<<<<<< HEAD
                .padding(top = Spacing.xl)
=======
                .padding(top = 32.dp)
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
        ) {
            Text(text = stringResource(id = R.string.action_start))
        }
    }
}
