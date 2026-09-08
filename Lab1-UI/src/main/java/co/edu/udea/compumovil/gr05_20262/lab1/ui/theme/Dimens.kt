package co.edu.udea.compumovil.gr05_20262.lab1.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Escala de espaciados única para toda la app. Usar siempre estos valores
 * (en vez de escribir "8.dp", "16.dp" sueltos en cada pantalla) hace que
 * el ritmo visual sea consistente y que ajustar el "aire" de la app sea
 * un cambio en un solo lugar.
 */
object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
}

/** Radio de esquina consistente para Cards y contenedores. */
object Shapes {
    val cardCorner = 16.dp
}
