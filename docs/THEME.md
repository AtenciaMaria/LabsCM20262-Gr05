# Sistema de temas (`ui/theme/`)

> Material 3 con una paleta propia teal/amber/indigo. Mantiene el
> contraste tanto en modo claro como en oscuro, y deja la puerta
> abierta al color dinámico en Android 12+.

Archivos:

- `Color.kt` — valores crudos `Color(0xFF…)`.
- `Dimens.kt` — `Spacing` (dps) y `Shapes` (radios).
- `Type.kt` — `Typography` con la escala usada.
- `Theme.kt` — `LabsTheme` Composable + `lightColorScheme` /
  `darkColorScheme`.

---

## 1. Paleta (`Color.kt`)

Familia **teal** como color primario (transmite confianza y orden),
**amber** como secundario (acentos cálidos) e **indigo** como
terciario (acentos fríos). Cada color tiene su contraparte `on*` que
Material 3 usa para texto/iconos sobre él.

### Modo claro

| Token                  | Hex       | Uso                                      |
|------------------------|-----------|------------------------------------------|
| `TealPrimary`          | `#00695C` | App bar, FAB, acentos principales.       |
| `TealPrimaryContainer` | `#B2DFDB` | Fondo del badge en `Welcome`.            |
| `OnTealPrimaryContainer` | `#00251F`| Icono dentro del badge.                  |
| `AmberSecondary`       | `#6D5E00` | Acentos cálidos.                         |
| `AmberSecondaryContainer` | `#FFE083` | Fondo de elementos secundarios.       |
| `OnAmberSecondaryContainer` | `#221B00` | Texto sobre el contenedor.            |
| `IndigoTertiary`       | `#3F51B5` | Acentos fríos (enlaces, info).           |
| `IndigoTertiaryContainer` | `#DEE0FF` | Fondo de elementos terciarios.         |
| `OnIndigoTertiaryContainer` | `#10143A` | Texto sobre el contenedor terciario.   |
| `LightBackground`      | `#F7FAF9` | Fondo de pantalla.                       |
| `LightSurface`         | `#FFFFFF` | Cards, TextFields.                       |
| `LightSurfaceVariant`  | `#E3EDEB` | Track de `LinearProgressIndicator`.      |
| `LightOutline`         | `#6F7977` | Bordes, `onSurfaceVariant`.              |

### Modo oscuro

| Token                       | Hex       | Notas                                  |
|-----------------------------|-----------|----------------------------------------|
| `TealPrimaryDark`           | `#80CBC4` | Tono más claro para mantener contraste.|
| `OnTealPrimaryDark`         | `#00382F` | Texto sobre `primary` oscuro.          |
| `TealPrimaryContainerDark`  | `#00504344`| (nota: ver observación)               |
| `OnTealPrimaryContainerDark`| `#B2DFDB` | Texto sobre contenedor oscuro.         |
| `AmberSecondaryDark`        | `#E7C64C` | Ámbar claro.                           |
| `OnAmberSecondaryDark`      | `#3A2F00` |                                        |
| `IndigoTertiaryDark`        | `#BFC4FF` |                                        |
| `OnIndigoTertiaryDark`      | `#1B2074` |                                        |
| `DarkBackground`            | `#0F1413` |                                        |
| `DarkSurface`               | `#171D1C` |                                        |
| `DarkSurfaceVariant`        | `#3F4947` |                                        |
| `DarkOutline`               | `#899391` |                                        |

> ⚠️ **Observación técnica:** `TealPrimaryContainerDark` está
> declarado como `0xFF00504344`, que parece un typo del original (5
> bytes en vez de 4). Material 3 espera un `Color(Int)` de 32 bits.
> Se recomienda revisar y reemplazar por un valor estándar (por ej.
> `#005048`). **No afecta el funcionamiento** porque `primaryContainer`
> en modo oscuro lo sobreescribe `OnTealPrimaryContainerDark`; ver
> `Theme.kt`.

### Error (compartido)

| Token              | Hex       |
|--------------------|-----------|
| `ErrorRed`         | `#BA1A1A` |
| `OnErrorRed`       | `#FFFFFF` |
| `ErrorContainerRed`| `#FFDAD6` |
| `OnErrorContainerRed` | `#410002`|

Material 3 recomienda que el rojo de error sea el mismo en ambos
modos para mantener la semántica universal de "error".

---

## 2. Espaciado y formas (`Dimens.kt`)

### `object Spacing`

Escala única de paddings y separaciones. **Usar siempre estos valores**
— nunca `8.dp`, `16.dp` sueltos en una pantalla.

| Token | Valor | Uso típico                                   |
|-------|-------|----------------------------------------------|
| `xs`  | 4.dp  | Padding entre label y contenido de un Card.  |
| `sm`  | 8.dp  | Separación entre campos del mismo Card.      |
| `md`  | 16.dp | Padding por defecto de Cards y de pantalla.  |
| `lg`  | 24.dp | Separación entre Cards.                      |
| `xl`  | 32.dp | Margen sobre CTAs (botones).                 |

### `object Shapes`

| Token        | Valor | Uso                                    |
|--------------|-------|----------------------------------------|
| `cardCorner` | 16.dp | Radio de las `Card` y del Surface del badge. |

---

## 3. Tipografía (`Type.kt`)

`val Typography: Typography` con la escala que la app **realmente usa**.

| Estilo            | Tamaño | Peso      | lineHeight | Uso                                 |
|-------------------|--------|-----------|------------|-------------------------------------|
| `headlineMedium`  | 28 sp  | Bold      | 36 sp      | `SectionTitle`                      |
| `titleLarge`      | 22 sp  | SemiBold  | 28 sp      | Títulos grandes (no usado aún).     |
| `titleMedium`     | 16 sp  | SemiBold  | 22 sp      | `CardSectionLabel`, labels de sección.|
| `bodyLarge`       | 16 sp  | Normal    | 24 sp      | Texto de inputs radio.              |
| `bodyMedium`      | 14 sp  | Normal    | 20 sp      | Texto descriptivo en `Welcome`.     |
| `labelLarge`      | 14 sp  | Medium    | 20 sp      | "Paso X de Y" en `StepProgress`.    |
| `labelMedium`     | 12 sp  | Medium    | 16 sp      | (Disponible para futuros hints).    |

Todos usan `FontFamily.Default` (Roboto en Android) — sin fuentes
custom para mantener el APK pequeño.

---

## 4. Tema raíz (`Theme.kt`)

### `private val LightColorScheme`

Instancia de `lightColorScheme(...)` con todos los roles M3 mapeados a
la paleta anterior. Importante:

- `onBackground = OnTealPrimaryContainer` — texto principal en
  tono casi‑negro azulado, no negro puro.
- `error` mapea a los rojos.

### `private val DarkColorScheme`

Igual pero con los valores `*Dark`. La barra de estado se pinta del
`primary` oscuro.

### `@Composable fun LabsTheme(darkTheme, dynamicColor, content)`

Decide qué `ColorScheme` usar y aplica el tema.

| Parámetro      | Tipo                  | Default                | Descripción                                      |
|----------------|-----------------------|------------------------|--------------------------------------------------|
| `darkTheme`    | `Boolean`             | `isSystemInDarkTheme()`| Si `true` usa `DarkColorScheme`.                 |
| `dynamicColor` | `Boolean`             | `false`                | Si `true` y SDK ≥ 31, usa paleta del sistema.    |
| `content`      | `@Composable ()->Unit`| —                      | Árbol de UI a renderizar.                        |

#### Lógica de selección

```kotlin
val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context)
        else           dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else      -> LightColorScheme
}
```

#### `SideEffect` para la barra de estado

```kotlin
SideEffect {
    val window = (view.context as Activity).window
    window.statusBarColor = colorScheme.primary.toArgb()
    WindowCompat.getInsetsController(window, view)
        .isAppearanceLightStatusBars = !darkTheme
}
```

Pinta la status bar del color `primary` y decide si sus iconos son
claros u oscuros. Se ejecuta en cada recomposición del tema, lo cual
está bien porque solo ocurre cuando cambia `darkTheme` o
`dynamicColor`.

#### Decisión `dynamicColor = false`

Antes estaba en `true`. Eso hacía que en Android 12+ la app tomara el
color de fondo del usuario y la paleta propia **nunca se veía**. Para
un laboratorio de diseño conviene ver la paleta por defecto; el
parámetro se mantiene por si el docente quiere probar el color
dinámico en vivo (`LabsTheme(dynamicColor = true) { … }`).

---

## Resumen

El tema está pensado para ser **modificable en un solo lugar**:

- Cambiar la marca → editar `Color.kt`.
- Cambiar la "densidad" → editar `Spacing`.
- Cambiar la escala tipográfica → editar `Type.kt`.
- Cambiar el comportamiento dinámico → `LabsTheme(dynamicColor = …)`.

Ningún composable debería tener un `Color(0xFF…)` o un `12.dp`
hardcodeado en producción; si lo encuentras, moverlo a su archivo
correspondiente del theme.
