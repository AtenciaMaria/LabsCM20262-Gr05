# Capa de modelo (`model/`)

> Modelos de datos, catálogos y utilidades **sin dependencias de
> Compose ni de Android UI**. Se pueden probar en JVM puro.

Archivos:

- `Forms.kt` — `PersonalData`, `ContactData`, extensiones, validadores
  y helpers de fecha.
- `Sex.kt` — enum `Sex`.
- `EducationLevel.kt` — enum `EducationLevel`.
- `Locations.kt` — objetos `LatinAmericaCountries`, `ColombianCities`.

---

## `Forms.kt`

### `data class PersonalData`

Representa los datos capturados en el paso 1.

| Campo              | Tipo                | Obligatorio | Notas                              |
|--------------------|---------------------|-------------|------------------------------------|
| `names`            | `String`            | ✅          | Trim aplicado en `toLog`.          |
| `surnames`         | `String`            | ✅          | Trim aplicado en `toLog`.          |
| `sex`              | `Sex?`              | ❌          | Default `null` (no seleccionado).  |
| `birthDateMillis`  | `Long?`             | ✅          | Epoch ms UTC para evitar zona horaria.|
| `education`        | `EducationLevel?`   | ❌          | Default `null`.                    |

#### `fun isValid(): Boolean`

Devuelve `true` solo si los **dos** campos obligatorios
(`names`, `surnames`) no están en blanco. La fecha de nacimiento **no**
se valida aquí porque la UI ya obliga a seleccionarla vía
`DatePicker`; si llega `null`, el formulario lo bloquea antes de
construir la instancia.

#### `fun toLog(context: Context): String`

Genera el bloque *“Información personal:”* que se imprime en Logcat.
Formato (ver `strings.xml`):

```
Información personal:
{nombres} {apellidos}
{sexo o "-"}
Nació el {fecha formateada o "-"}
{escolaridad o "-"}
```

- `dateText` se obtiene con `formatDate(context, millis)`.
- Si un campo opcional es `null` se imprime `-`.
- Usa `buildString` para acumular las líneas sin generar `String`
  intermedios.

#### `companion object`

##### `val labelHeader: Int`

`R.string.log_header_personal`. Constante reutilizable por si en el
futuro se quiere extraer el header sin instanciar `PersonalData`.

##### `fun formatDate(context: Context, millis: Long): String`

Devuelve la fecha en formato **locale-aware**:

- `es` → `dd/MM/yyyy`
- resto → `MM/dd/yyyy`

Usa `context.resources.configuration.locales[0]` para detectar el
idioma del sistema y `SimpleDateFormat(pattern, locale)` para que el
formato cambie automáticamente al cambiar de idioma.

### `data class ContactData`

Representa los datos capturados en el paso 2.

| Campo      | Tipo     | Obligatorio | Reglas de validación              |
|------------|----------|-------------|-----------------------------------|
| `phone`    | `String` | ✅          | `isPhoneValid()`: 7–15 chars, solo dígitos/+/‑/( )/espacio. |
| `address`  | `String` | ❌          | —                                 |
| `email`    | `String` | ✅          | `isEmailValid()`: matcher de `Patterns.EMAIL_ADDRESS`. |
| `country`  | `String` | ✅          | No vacío.                         |
| `city`     | `String` | ❌          | —                                 |

#### `fun isValid(): Boolean`

`true` si teléfono, email y país pasan todas las reglas. La dirección
y la ciudad son opcionales.

#### `fun toLog(context: Context): String`

Genera el bloque *“Información de contacto:”* para Logcat. **Omitirá
la línea de dirección y/o ciudad si están en blanco** (para no
ensuciar el log con campos vacíos opcionales).

```
Información de contacto:
Teléfono: {phone}
Dirección: {address}     ← solo si !isBlank
Email: {email}
País: {country}
Ciudad: {city}           ← solo si !isBlank
```

#### `companion object`

##### `fun isEmailValid(email: String): Boolean`

Delega en `android.util.Patterns.EMAIL_ADDRESS`. Matcher cacheado
internamente por Android, así que es eficiente.

##### `fun isPhoneValid(phone: String): Boolean`

Regla definida por el equipo (no es la regla ITU estricta):

```kotlin
phone.trim().length in 7..15 &&
phone.all { it.isDigit() || it in "+- ()" }
```

Acepta formatos como `+57 300 123 4567` o `(604) 555-1234`.

### Extensiones top‑level

```kotlin
fun Sex.label(context: Context): String = context.getString(labelRes)
fun EducationLevel.label(context: Context): String = context.getString(labelRes)
```

Devuelven el nombre localizado del enum usando el `Context`. Se usan
tanto en UI como en `toLog` para no acoplar los Composables al `R`.

### `fun todayMinus(years: Int): Long`

Helper que devuelve `now − years` como epoch ms. **Actualmente no se
usa en la app** (la fecha de nacimiento no se acota a un rango), pero
está disponible para imponer límites en el `DatePickerField` (por
ej. `DatePickerState.yearRange = 1900..todayMinus(5)`). Ver
[`DIAGRAMS.md`](DIAGRAMS.md#7-sistema-de-temas) sección de mejoras
futuras.

---

## `Sex.kt`

### `enum class Sex(@StringRes val labelRes: Int)`

| Valor   | `labelRes`        |
|---------|-------------------|
| `MALE`  | `R.string.sex_male`   |
| `FEMALE`| `R.string.sex_female` |
| `OTHER` | `R.string.sex_other`  |

#### `companion object.fromKey(key: String?): Sex?`

Convierte la clave guardada en `rememberSaveable` (`String?`) de vuelta
al enum. Devuelve `null` si `key == null` o si no coincide con ningún
nombre de la enumeración.

Se usa en `PersonalDataScreen` para reconstruir el estado después de
rotación:

```kotlin
SexSelector(
    selected = Sex.fromKey(sexKey),
    onSelect = { sexKey = it.name }
)
```

---

## `EducationLevel.kt`

### `enum class EducationLevel(@StringRes val labelRes: Int)`

| Valor          | `labelRes`                 |
|----------------|----------------------------|
| `PRIMARY`      | `R.string.edu_primary`     |
| `SECONDARY`    | `R.string.edu_secondary`   |
| `UNDERGRADUATE`| `R.string.edu_undergraduate`|
| `POSTGRADUATE` | `R.string.edu_postgraduate`|

#### `companion object.fromKey(key: String?): EducationLevel?`

Idéntico a `Sex.fromKey`. Permite persistir la selección del dropdown
como `String?` y reconstruir el enum al recomponer.

---

## `Locations.kt`

### `object LatinAmericaCountries`

Catálogo estático de los 20 países de Latinoamérica que aparecen en el
`AutocompleteField` de país. Lista en español (orden alfabético).

```kotlin
val all: List<String>
```

**Por qué un `object` y no un enum:** el enunciado pide *autocompletar
sobre una lista*, no un conjunto cerrado. Mantenerlo como
`List<String>` permite añadir/quitar entradas sin tocar tipos.

### `object ColombianCities`

Catálogo estático de **40 ciudades** principales de Colombia para el
`AutocompleteField` de ciudad. Incluye capitales departamentales más
algunas ciudades metropolitanas relevantes (Envigado, Bello, Soacha,
etc.). Lista en español, alfabética.

> El enunciado sugiere como **bonificación (0.4)** conectar un API para
> expandir las ciudades; ver [`DIAGRAMS.md`](DIAGRAMS.md) sección de
> posibles evoluciones.

---

## Resumen de funciones del modelo

| Función                                          | Archivo         | Propósito                                          |
|--------------------------------------------------|-----------------|----------------------------------------------------|
| `PersonalData.isValid()`                         | `Forms.kt`      | Valida obligatorios de paso 1.                     |
| `PersonalData.toLog(Context)`                    | `Forms.kt`      | Texto de Logcat para paso 1.                       |
| `PersonalData.Companion.labelHeader`             | `Forms.kt`      | `R.string.log_header_personal`.                    |
| `PersonalData.Companion.formatDate(Context,Long)`| `Forms.kt`      | Fecha localizada dd/MM o MM/dd.                    |
| `ContactData.isValid()`                          | `Forms.kt`      | Valida obligatorios + formato de paso 2.           |
| `ContactData.toLog(Context)`                     | `Forms.kt`      | Texto de Logcat para paso 2.                       |
| `ContactData.Companion.isEmailValid(String)`     | `Forms.kt`      | Matcher `Patterns.EMAIL_ADDRESS`.                  |
| `ContactData.Companion.isPhoneValid(String)`     | `Forms.kt`      | Longitud 7–15 y caracteres permitidos.             |
| `Sex.label(Context)`                             | `Forms.kt`      | Texto localizado del enum.                         |
| `EducationLevel.label(Context)`                  | `Forms.kt`      | Texto localizado del enum.                         |
| `todayMinus(Int)`                                | `Forms.kt`      | Epoch ms de hoy menos N años.                      |
| `Sex.Companion.fromKey(String?)`                 | `Sex.kt`        | `String? → Sex?` (round‑trip de persistencia).     |
| `EducationLevel.Companion.fromKey(String?)`      | `EducationLevel.kt` | `String? → EducationLevel?`.                    |
| `LatinAmericaCountries.all`                      | `Locations.kt`  | Lista de países.                                   |
| `ColombianCities.all`                            | `Locations.kt`  | Lista de ciudades.                                 |
