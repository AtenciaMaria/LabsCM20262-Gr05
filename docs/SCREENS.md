# Pantallas (Activities)

> Cada Activity se divide en tres bloques:
>
> 1. **`Activity`** (`ComponentActivity`) — entry point, configura el
>    tema y monta el `Scaffold`.
> 2. **TopBar** (`@Composable private fun XxxTopBar()`) — barra
>    superior.
> 3. **Screen** (`@Composable fun XxxScreen(...)`) — composable
>    público con todo el formulario. Aislado de la Activity para
>    permitir previews y reuso.

---

## Índice

1. [`MainActivity` + `WelcomeScreen`](#1-mainactivity--welcomescreen)
2. [`PersonalDataActivity` + `PersonalTopBar` + `PersonalDataScreen`](#2-personaldataactivity)
3. [`ContactDataActivity` + `ContactTopBar` + `ContactDataScreen`](#3-contactdataactivity)

---

## 1. `MainActivity` + `WelcomeScreen`

**Archivo:** `MainActivity.kt`

### `class MainActivity : ComponentActivity()`

Único punto de entrada (`LAUNCHER` en el manifest).

#### `override fun onCreate(savedInstanceState: Bundle?)`

1. `enableEdgeToEdge()` — dibuja bajo las barras de sistema.
2. `setContent { LabsTheme { … } }` — monta el árbol de Compose.
3. Renderiza un `Scaffold` que envuelve `WelcomeScreen`.
4. El callback `onStart` del botón *Comenzar* abre `PersonalDataActivity`
   con `startActivity(Intent(this, PersonalDataActivity::class.java))`.

### `@Composable fun WelcomeScreen(modifier, onStart)`

Pantalla de bienvenida. Estructura vertical centrada:

```
[ Surface circular (ícono) ]
      Bienvenido
   Laboratorio 1 — ...
Esta aplicación captura los datos ...

       [ Comenzar ]
```

| Parámetro  | Tipo            | Descripción                                  |
|------------|-----------------|----------------------------------------------|
| `modifier` | `Modifier`      | Aplicado al `Column` raíz.                   |
| `onStart`  | `() -> Unit`    | Click del botón *Comenzar*.                  |

**Detalles**

- El badge usa `MaterialTheme.colorScheme.primaryContainer` /
  `onPrimaryContainer` para mantener el contraste en ambos modos.
- Textos largos centrados con `textAlign = TextAlign.Center`.
- Espaciados vienen de `Spacing.lg`, `Spacing.sm`, `Spacing.xl`.

---

## 2. `PersonalDataActivity`

**Archivo:** `PersonalDataActivity.kt`

### `class PersonalDataActivity : ComponentActivity()`

#### `override fun onCreate(savedInstanceState: Bundle?)`

1. `enableEdgeToEdge()`.
2. `setContent { LabsTheme { … } }`.
3. `Scaffold(topBar = { PersonalTopBar() })` con padding aplicado al
   contenido.
4. `PersonalDataScreen` recibe `onNext`:
   - `Log.d(getString(R.string.log_tag), data.toLog(this))`
   - `startActivity(Intent(this, ContactDataActivity::class.java))`.

### `@Composable private fun PersonalTopBar()`

`TopAppBar` con:

- Título: `R.string.title_personal_data`.
- `containerColor = primary`, `titleContentColor = onPrimary`.

### `@Composable private fun CardSectionLabel(text: String)`

Texto pequeño (`titleMedium`, color `primary`) usado como
encabezado de cada `Card`. Es `private` porque solo se usa dentro de
este archivo; si otra pantalla lo necesitara habría que moverlo a
`ui/components/`.

### `@Composable fun PersonalDataScreen(modifier, onNext)`

Función principal. Contiene todo el formulario del paso 1.

#### Adaptación a orientación

Se detecta con `LocalConfiguration.current`:

```kotlin
val configuration = LocalConfiguration.current
val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
```

- **Portrait** (default): nombres y apellidos apilados; botón
  *Siguiente* al final del formulario, debajo del dropdown.
- **Landscape**: nombres y apellidos lado a lado (mismo `Row`,
  `Modifier.weight(1f)`); la fecha y el dropdown se acotan a
  `280.dp` de ancho; el botón *Siguiente* se reubica a la derecha
  del dropdown para aprovechar el ancho disponible.

El `SexSelector` se le pasa con `horizontal = true` y
`centered = isLandscape` (la versión horizontal con centrado funciona
bien cuando hay mucho espacio horizontal).

#### Estado interno

```kotlin
var names        by rememberSaveable { mutableStateOf("") }
var surnames     by rememberSaveable { mutableStateOf("") }
var sexKey       by rememberSaveable { mutableStateOf<String?>(null) }
var birthDateMillis by rememberSaveable { mutableStateOf<Long?>(null) }
var educationKey by rememberSaveable { mutableStateOf<String?>(null) }

var namesError       by rememberSaveable { mutableStateOf(false) }
var surnamesError    by rememberSaveable { mutableStateOf(false) }
var birthDateError   by rememberSaveable { mutableStateOf(false) }
```

| Variable            | Por qué `String?` y no el enum directo                          |
|---------------------|-----------------------------------------------------------------|
| `sexKey`            | `Bundle` no soporta enums; se guarda `name` y se reconstruye con `Sex.fromKey`. |
| `educationKey`      | Igual que arriba con `EducationLevel.fromKey`.                  |

#### Estructura visual

```
[ SectionTitle · "Información personal" ]
[ StepProgress · 1/2 ]

┌── Card · "Datos básicos" ─────────────────────────┐
│ OutlinedTextField · nombres                       │
│ OutlinedTextField · apellidos                     │
│ SexSelector                                       │
└───────────────────────────────────────────────────┘

┌── Card · "Datos adicionales" ─────────────────────┐
│ DatePickerField                                   │
│ EducationDropdown                                 │
└───────────────────────────────────────────────────┘

       [ Siguiente ]
```

#### Modifier chain (clave para el requisito "teclado no oculta el campo")

```kotlin
Column(
    modifier = modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .imePadding()
        .navigationBarsPadding()
        .padding(horizontal = Spacing.md, vertical = Spacing.md),
    verticalArrangement = Arrangement.Top
)
```

- `verticalScroll`: permite scrollear si el contenido no cabe.
- `imePadding`: empuja el contenido hacia arriba cuando aparece el
  teclado.
- `navigationBarsPadding`: respeta la barra inferior del sistema.

#### onClick del botón *Siguiente*

```kotlin
onClick = {
    namesError       = names.isBlank()
    surnamesError    = surnames.isBlank()
    birthDateError   = birthDateMillis == null
    if (namesError || surnamesError || birthDateError) return@Button

    val data = PersonalData(
        names           = names,
        surnames        = surnames,
        sex             = Sex.fromKey(sexKey),
        birthDateMillis = birthDateMillis,
        education       = EducationLevel.fromKey(educationKey)
    )
    onNext(data)
}
```

Lógica en cuatro pasos:

1. Marca los errores en línea según estado actual.
2. Si alguno es `true`, aborta (`return@Button`) — los Composables
   ya están en estado de error.
3. Construye el `PersonalData`.
4. Llama `onNext(data)`, que (definido en la Activity) loguea y abre
   la siguiente pantalla.

#### Nota sobre `imeAction = ImeAction.Next`

En cada `OutlinedTextField` se configura explícitamente:

```kotlin
keyboardOptions = KeyboardOptions(
    keyboardType      = KeyboardType.Text,
    capitalization    = KeyboardCapitalization.Words,
    autoCorrectEnabled = false,
    imeAction         = ImeAction.Next
)
```

Cumple dos requisitos del enunciado:

- *Mayúscula inicial*: `capitalization = Words`.
- *Sin sugerencias*: `autoCorrectEnabled = false`.
- *Teclado no sugiere*: `autoCorrectEnabled = false` también bloquea
  el autocompletado.

---

## 3. `ContactDataActivity`

**Archivo:** `ContactDataActivity.kt`

### `class ContactDataActivity : ComponentActivity()`

#### `override fun onCreate(savedInstanceState: Bundle?)`

1. `enableEdgeToEdge()`.
2. `setContent { LabsTheme { … } }`.
3. `Scaffold(topBar = { ContactTopBar() })`.
4. `ContactDataScreen` con `onSubmit`:
   - `Log.d(getString(R.string.log_tag), data.toLog(this))`.
   - **No abre** otra Activity — al ser el último paso, dispara un
     `AlertDialog` (manejado dentro de `ContactDataScreen`).

### `@Composable private fun ContactTopBar()`

Idéntico patrón que `PersonalTopBar`, pero con
`R.string.title_contact_data`.

### `@Composable private fun CardSectionLabel(text: String)`

Mismo componente que en `PersonalDataActivity` (duplicado a propósito
porque es `private`). Si se quisiera DRY habría que promoverlo a
`ui/components/`.

### `@Composable fun ContactDataScreen(modifier, onSubmit)`

Formulario del paso 2 + diálogo de confirmación.

#### Adaptación a orientación

Mismo mecanismo que `PersonalDataScreen`:

- **Portrait** (default): campos a `fillMaxWidth`, botón
  *Finalizar* alineado al inicio (`Arrangement.Start`).
- **Landscape**: el `Card` de contacto se reorganiza para aprovechar
  el ancho (algunos campos lado a lado); los autocompletes de país y
  ciudad se acotan a `200.dp` para evitar líneas demasiado largas.

#### Estado interno

```kotlin
var phone by rememberSaveable { mutableStateOf("") }
var address by rememberSaveable { mutableStateOf("") }
var email by rememberSaveable { mutableStateOf("") }
var country by rememberSaveable { mutableStateOf("") }
var city by rememberSaveable { mutableStateOf("") }

var phoneError by rememberSaveable { mutableStateOf(false) }
var emailError by rememberSaveable { mutableStateOf(false) }
var countryError by rememberSaveable { mutableStateOf(false) }

var showDialog by rememberSaveable { mutableStateOf(false) }
```

#### Estructura visual

```
[ SectionTitle · "Información de contacto" ]
[ StepProgress · 2/2 ]

┌── Card · "Cómo contactarte" ──────────────────────┐
│ OutlinedTextField · teléfono  (KeyboardType.Phone)│
│ OutlinedTextField · dirección                    │
│ OutlinedTextField · email     (KeyboardType.Email)│
└───────────────────────────────────────────────────┘

┌── Card · "Ubicación" ─────────────────────────────┐
│ AutocompleteField · país (LatinAmericaCountries)  │
│ AutocompleteField · ciudad (ColombianCities)      │
└───────────────────────────────────────────────────┘

       [ Finalizar ]
```

#### onClick del botón *Finalizar*

```kotlin
onClick = {
    phoneError  = !ContactData.isPhoneValid(phone)
    emailError  = !ContactData.isEmailValid(email)
    countryError = country.isBlank()
    if (phoneError || emailError || countryError) return@Button

    val data = ContactData(
        phone    = phone.trim(),
        address  = address.trim(),
        email    = email.trim(),
        country  = country.trim(),
        city     = city.trim()
    )
    onSubmit(data)
    showDialog = true
}
```

Notas:

- Aplica `.trim()` antes de guardar para evitar espacios en blanco
  fantasma que luego ensuciarían Logcat.
- La dirección y ciudad **no** se validan (son opcionales).
- `onSubmit(data)` registra en Logcat; luego se muestra el
  `AlertDialog`.

#### AlertDialog de confirmación

```kotlin
if (showDialog) {
    AlertDialog(
        onDismissRequest = { showDialog = false },
        title    = { Text(text = stringResource(id = R.string.confirm_title)) },
        text     = { Text(text = stringResource(id = R.string.confirm_message)) },
        confirmButton = {
            TextButton(onClick = { showDialog = false }) {
                Text(text = stringResource(id = R.string.confirm_button))
            }
        }
    )
}
```

- Título: `R.string.confirm_title` → *"Datos enviados"*.
- Mensaje: `R.string.confirm_message` → *"Tu información fue
  registrada. Revisa Logcat para ver los datos."*
- Botón único: *"Entendido"*.

#### Teclados por campo

| Campo      | `KeyboardType`     | `ImeAction` | Por qué                              |
|------------|--------------------|-------------|--------------------------------------|
| Teléfono   | `Phone`            | `Next`      | Teclado numérico con `*`/`#`.        |
| Dirección  | `Text`             | `Next`      | Normal, mayúscula inicial, sin autocompletar. |
| Email      | `Email`            | `Next`      | Teclado con `@` accesible.           |
| País       | `Text`             | `Next`      | Para buscar.                         |
| Ciudad     | `Text`             | `Done`      | Última entrada del flujo.            |

---

## Resumen de Activities

| Activity              | Pantalla               | Campos                | Callback       |
|-----------------------|------------------------|-----------------------|----------------|
| `MainActivity`        | `WelcomeScreen`        | (solo entrada)        | abre Personal  |
| `PersonalDataActivity`| `PersonalDataScreen`   | 5 (3 obligatorios)    | log + Contact  |
| `ContactDataActivity` | `ContactDataScreen`    | 5 (3 obligatorios)    | log + AlertDialog |

Todas usan `enableEdgeToEdge()`, `LabsTheme`, `Scaffold` con `TopAppBar`,
y la misma escala de espaciados (`Spacing.md`/`lg`). Esta consistencia
es la que da la sensación de "una sola app" pese a ser 3 pantallas
distintas.
