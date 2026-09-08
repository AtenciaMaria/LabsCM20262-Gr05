package co.edu.udea.compumovil.gr05_20262.lab1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

<<<<<<< HEAD
// Se declaran explícitamente los "roles" de color de Material 3 (no solo
// primary/secondary/tertiary) para que cada componente -Card, TextField,
// TopAppBar- tenga un color coherente con la paleta, en vez de heredar
// los valores por defecto que genera Android Studio.
private val LightColorScheme = lightColorScheme(
    primary = TealPrimary,
    onPrimary = LightSurface,
    primaryContainer = TealPrimaryContainer,
    onPrimaryContainer = OnTealPrimaryContainer,
    secondary = AmberSecondary,
    onSecondary = LightSurface,
    secondaryContainer = AmberSecondaryContainer,
    onSecondaryContainer = OnAmberSecondaryContainer,
    tertiary = IndigoTertiary,
    onTertiary = LightSurface,
    tertiaryContainer = IndigoTertiaryContainer,
    onTertiaryContainer = OnIndigoTertiaryContainer,
    background = LightBackground,
    onBackground = OnTealPrimaryContainer,
    surface = LightSurface,
    onSurface = OnTealPrimaryContainer,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOutline,
    outline = LightOutline,
    error = ErrorRed,
    onError = OnErrorRed,
    errorContainer = ErrorContainerRed,
    onErrorContainer = OnErrorContainerRed
)

private val DarkColorScheme = darkColorScheme(
    primary = TealPrimaryDark,
    onPrimary = OnTealPrimaryDark,
    primaryContainer = TealPrimaryContainerDark,
    onPrimaryContainer = OnTealPrimaryContainerDark,
    secondary = AmberSecondaryDark,
    onSecondary = OnAmberSecondaryDark,
    tertiary = IndigoTertiaryDark,
    onTertiary = OnIndigoTertiaryDark,
    background = DarkBackground,
    onBackground = DarkSurfaceVariant,
    surface = DarkSurface,
    onSurface = DarkSurfaceVariant,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOutline,
    outline = DarkOutline,
    error = ErrorRed,
    onError = OnErrorRed,
    errorContainer = ErrorContainerRed,
    onErrorContainer = OnErrorContainerRed
=======
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
)

@Composable
fun LabsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
<<<<<<< HEAD
    // Antes estaba en "true": eso hace que en Android 12+ la app tome
    // el color de fondo del usuario y tu paleta nunca se vea. Para un
    // lab de diseño conviene mostrar tu propia paleta por defecto; se
    // deja el parámetro por si el docente pide probar el color dinámico.
    dynamicColor: Boolean = false,
=======
    dynamicColor: Boolean = true,
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
<<<<<<< HEAD
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
=======
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
>>>>>>> 1fd30290d2eb581599e949452b543c53dac9ca26
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
