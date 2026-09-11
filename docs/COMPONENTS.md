# Componentes de UI (`ui/components/`)

> Cada componente es **stateless** (no guarda estado del negocio) y
> expone su contrato con `value` + callbacks (`onValueChange`,
> `onSelect`). Esto permite previsualizarlos con `@Preview` y
> reutilizarlos en otras pantallas.

Índice:

1. [`SectionTitle`](#1-sectiontitle)
2. [`StepProgress`](#2-stepprogress)
3. [`SexSelector`](#3-sexselector)
4. [`EducationDropdown`](#4-educationdropdown)
5. [`DatePickerField`](#5-datepickerfield)
6. [`AutocompleteField`](#6-autocompletefield)

---

## 1. `SectionTitle`

**Archivo:** `SectionTitle.kt`

```kotlin
@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier)
```

Cabecera grande que se muestra al inicio de cada pantalla (después del
`TopAppBar`). Usa `headlineMedium` con color `primary` para anclar
visualmente la pantalla.

| Parámetro  | Tipo        | Descripción                                  |
|------------|-------------|----------------------------------------------|
| `text`     | `String`    | Texto a mostrar (ya localizado).             |
| `modifier` | `Modifier`  | Para ajustes externos; por defecto sin nada. |

**Diseño interno**

- Estilo: `MaterialTheme.typography.headlineMedium`.
- Color: `MaterialTheme.colorScheme.primary`.
- Padding vertical: `8.dp`.

---

## 2. `StepProgress`

**Archivo:** `StepProgress.kt`

```kotlin
@Composable
fun StepProgress(currentStep: Int, totalSteps: Int, modifier: Modifier = Modifier)
```

Indicador *“Paso X de Y”* con barra horizontal. Convierte dos pantallas
sueltas en un flujo perceptible: el usuario siempre sabe dónde está y
cuánto falta.

| Parámetro      | Tipo        | Descripción                                     |
|----------------|-------------|-------------------------------------------------|
| `currentStep`  | `Int`       | Paso actual (1‑based).                          |
| `totalSteps`   | `Int`       | Total de pasos del flujo.                       |
| `modifier`     | `Modifier`  | Para padding externo, alineación, etc.          |

**Diseño interno**

- Texto: `R.string.step_indicator` → `"Paso %1$d de %2$d"`.
- Barra: `LinearProgressIndicator` con
  `progress = currentStep / totalSteps` (lambda de M3).
- Color barra: `primary`. Color track: `surfaceVariant`.

**Uso esperado**

```kotlin
StepProgress(currentStep = 1, totalSteps = 2) // PersonalData
StepProgress(currentStep = 2, totalSteps = 2) // ContactData
```

---

## 3. `SexSelector`

**Archivo:** `SexSelector.kt`

```kotlin
@Composable
fun SexSelector(
    selected: Sex?,
    onSelect: (Sex) -> Unit,
    modifier: Modifier = Modifier
)
```

Grupo de *radio buttons* (Material 3) que renderiza las opciones del
enum `Sex`. El **toda la fila es seleccionable** (no solo el círculo),
gracias al modificador `selectable`, lo que mejora la accesibilidad.

| Parámetro   | Tipo                  | Descripción                                  |
|-------------|-----------------------|----------------------------------------------|
| `selected`  | `Sex?`                | Valor actual (o `null` si nada seleccionado).|
| `onSelect`  | `(Sex) -> Unit`       | Se invoca con el valor elegido.              |
| `modifier`  | `Modifier`            | Para ajustes externos.                       |

**Diseño interno**

- Label superior: `R.string.lbl_sex`.
- Itera `Sex.entries` y dibuja una `Row` con:
  - `RadioButton(selected = …, onClick = null)` — el `onClick`
    real vive en la `Row` vía `Modifier.selectable`, evitando doble
    disparador.
  - `Text` con `stringResource(sex.labelRes)`.
- `Role.RadioButton` para que TalkBack lo lea correctamente.

---

## 4. `EducationDropdown`

**Archivo:** `EducationDropdown.kt`

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationDropdown(
    selected: EducationLevel?,
    onSelect: (EducationLevel) -> Unit,
    modifier: Modifier = Modifier
)
```

Selector tipo *spinner* (Material 3 `ExposedDropdownMenuBox`) que
muestra las opciones del enum `EducationLevel`.

| Parámetro   | Tipo                    | Descripción                                 |
|-------------|-------------------------|---------------------------------------------|
| `selected`  | `EducationLevel?`       | Nivel actual (o `null`).                     |
| `onSelect`  | `(EducationLevel) -> Unit` | Notifica la elección.                    |
| `modifier`  | `Modifier`              | Ajuste externo.                              |

**Diseño interno**

- `OutlinedTextField` de solo lectura (`onValueChange = {}`),
  muestra el label localizado del enum seleccionado.
- `trailingIcon`: `Icons.Filled.ArrowDropDown`.
- `menuAnchor()` enlaza el TextField con el menú desplegable.
- Cada `DropdownMenuItem` muestra `stringResource(level.labelRes)`.
- Al hacer click se llama `onSelect(level)` y se cierra el menú.

---

## 5. `DatePickerField`

**Archivo:** `DatePickerField.kt`

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    value: Long?,
    onValueChange: (Long) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
)
```

Campo de solo lectura que abre un `DatePickerDialog` (Material 3) al
tocar el icono de calendario. Al confirmar, propaga la fecha
seleionada en **epoch ms UTC** vía `onValueChange`.

| Parámetro        | Tipo                | Descripción                                         |
|------------------|---------------------|-----------------------------------------------------|
| `value`          | `Long?`             | Fecha actual (epoch ms). `null` si no se eligió.    |
| `onValueChange`  | `(Long) -> Unit`    | Callback con la nueva fecha (epoch ms).             |
| `modifier`       | `Modifier`          | Ajuste externo.                                     |
| `isError`        | `Boolean`           | Si `true`, muestra el TextField en estado de error. |
| `errorMessage`   | `String?`           | Texto debajo del campo si hay error.                |

**Diseño interno**

- Estado local `var open by remember { mutableStateOf(false) }` para
  mostrar/ocultar el diálogo.
- Texto mostrado: `PersonalData.formatDate(context, value)` o
  `R.string.date_placeholder` ("Sin seleccionar") si es `null`.
- `OutlinedTextField` con `readOnly = true` y `onValueChange = {}`
  para impedir edición directa.
- `trailingIcon`: `IconButton` con `Icons.Filled.CalendarToday` que
  abre el diálogo.
- Al confirmar, `datePickerState.selectedDateMillis?.let { onValueChange(it) }`.

**Decisión de diseño:** se optó por **no acotar el rango de años**
(`yearRange`) para simplificar. Si se quisiera, se puede pasar
`yearRange = IntRange(1900, …)` al `rememberDatePickerState`.

---

## 6. `AutocompleteField`

**Archivo:** `AutocompleteField.kt`

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutocompleteField(
    labelRes: Int,
    placeholderRes: Int,
    options: List<String>,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    imeAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
)
```

`OutlinedTextField` con menú desplegable que **filtra las opciones en
vivo** según lo que el usuario escribe (case‑insensitive). Si el
contenido está vacío, muestra todas las opciones.

| Parámetro        | Tipo                    | Descripción                                |
|------------------|-------------------------|--------------------------------------------|
| `labelRes`       | `Int`                   | `@StringRes` de la etiqueta.               |
| `placeholderRes` | `Int`                   | `@StringRes` del placeholder.              |
| `options`        | `List<String>`          | Lista completa de opciones.                |
| `value`          | `String`                | Valor actual del campo.                    |
| `onValueChange`  | `(String) -> Unit`      | Notifica cada cambio (incluye selección).  |
| `errorMessage`   | `String?`               | Si no nulo, marca el campo como error.     |
| `imeAction`      | `ImeAction`             | Acción del teclado (default `Next`).       |
| `modifier`       | `Modifier`              | Ajuste externo.                            |

**Diseño interno**

- Estado local: `var expanded by remember { mutableStateOf(false) }`.
- `filtered` se recalcula con `remember(value, options)`:
  - Si `value` está en blanco → todas las opciones.
  - Si no → `options.filter { it.contains(value, ignoreCase = true) }`.
- `ExposedDropdownMenuBox`:
  - `expanded` se muestra solo si hay opciones filtradas
    (`expanded && filtered.isNotEmpty()`).
  - Al escribir se fuerza `expanded = true`.
  - `trailingIcon`: `Icons.Filled.ArrowDropDown`.
- Cada `DropdownMenuItem` propaga la opción seleccionada y cierra el
  menú.

**Detalle:** cuando no hay coincidencias el menú se cierra solo
(porque `expanded && filtered.isNotEmpty()`). Esto guía al usuario a
refinar la búsqueda.

---

## Resumen rápido

| Componente           | Tipo de control    | Estado local  | Estado que recibe      |
|----------------------|--------------------|---------------|------------------------|
| `SectionTitle`       | Text               | —             | `text: String`         |
| `StepProgress`       | ProgressIndicator  | —             | `currentStep, totalSteps`|
| `SexSelector`        | RadioButton group  | —             | `selected: Sex?`       |
| `EducationDropdown`  | ExposedDropdown    | `expanded`    | `selected: Edu?`       |
| `DatePickerField`    | DatePicker dialog  | `open`        | `value: Long?`         |
| `AutocompleteField`  | ExposedDropdown    | `expanded`    | `value: String`        |

Todos usan `MaterialTheme` para colores y tipografía, por lo que
cambian automáticamente con el modo claro/oscuro y los colores
dinámicos (Android 12+).
