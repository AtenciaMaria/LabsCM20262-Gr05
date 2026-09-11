# Arquitectura del Laboratorio 1

> Aplicación Android en **Kotlin + Jetpack Compose** que captura datos
> personales y de contacto de un usuario en un flujo de 2 pasos y los
> registra en `Logcat`.

---

## 1. Vista general

La app sigue una arquitectura **monolítica por capas simples**, adecuada
al alcance del laboratorio. No hay ViewModel ni DI: el estado vive
directamente en las pantallas Compose con `rememberSaveable`, lo cual
cumple el requisito de **persistir durante cambios de configuración**
(rotación, tema, idioma) sin agregar complejidad.

```
┌──────────────────────────────────────────────────────────────┐
│                       AndroidManifest                         │
│   MainActivity (LAUNCHER) → PersonalDataActivity             │
│                            → ContactDataActivity              │
└──────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                  Capa de presentación (UI)                    │
│                                                              │
│   MainActivity.kt      → WelcomeScreen                       │
│   PersonalDataActivity → PersonalTopBar + PersonalDataScreen │
│   ContactDataActivity  → ContactTopBar  + ContactDataScreen  │
│                                                              │
│   Componentes reutilizables (ui/components/)                 │
│   • SectionTitle • StepProgress • SexSelector                 │
│   • EducationDropdown • DatePickerField • AutocompleteField  │
│                                                              │
│   Tema (ui/theme/)                                           │
│   • Color • Dimens (Spacing, Shapes) • Type • Theme           │
└──────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                       Capa de modelo                         │
│                                                              │
│   model/Forms.kt    → PersonalData, ContactData (data class) │
│                       + label() extensions                   │
│                       + todayMinus() helper                  │
│                       + isEmailValid() / isPhoneValid()      │
│   model/Sex.kt      → enum Sex { MALE, FEMALE, OTHER }       │
│   model/EducationLevel.kt → enum EducationLevel (4 valores)  │
│   model/Locations.kt → objetos LatinAmericaCountries,        │
│                        ColombianCities (catálogos estáticos) │
└──────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                       Recursos (res/)                         │
│                                                              │
│   values/strings.xml (es)  → todos los textos en español     │
│   values-en/strings.xml    → mismos textos en inglés         │
│   values/themes.xml        → puente al tema Compose          │
│   drawable/, mipmap-*/     → ícono personalizado             │
└──────────────────────────────────────────────────────────────┘
```

---

## 2. Stack técnico

| Capa            | Tecnología                                |
|-----------------|-------------------------------------------|
| Lenguaje        | Kotlin                                     |
| UI              | Jetpack Compose + Material 3              |
| Estado          | `rememberSaveable` (sin ViewModel)        |
| Navegación      | `Intent` explícito entre `ComponentActivity` |
| Internacionalización | `res/values/` + `res/values-en/`     |
| Logging         | `android.util.Log`                        |
| Build           | Gradle Kotlin DSL (`build.gradle.kts`)    |

---

## 3. Capas y sus responsabilidades

### 3.1 Capa de presentación

- **`MainActivity`**: pantalla de bienvenida. Único punto de entrada
  (`LAUNCHER`). Su única responsabilidad es lanzar `PersonalDataActivity`.
- **`PersonalDataActivity`**: paso 1 del flujo. Captura nombres,
  apellidos, sexo, fecha de nacimiento y escolaridad. Al pulsar
  *Siguiente* valida campos obligatorios, registra en `Logcat` y abre
  `ContactDataActivity`.
- **`ContactDataActivity`**: paso 2 del flujo. Captura teléfono,
  dirección, email, país y ciudad. Al pulsar *Finalizar* valida formato
  de teléfono y email + obligatorios, registra en `Logcat` y muestra
  `AlertDialog` de confirmación.

Cada `Activity` se divide en:

1. `XxxTopBar()`: barra superior privada (Material 3 `TopAppBar`).
2. `XxxScreen()`: composable público que contiene todo el formulario,
   recibe callbacks (`onNext` / `onSubmit`) por parámetro. **Esto
   permite previsualizar la pantalla sin lanzar la Activity** (útil
   para Compose Previews).

### 3.2 Capa de componentes (`ui/components/`)

Componentes reutilizables, **agnósticos de la Activity** que los usa.
Recién en estado vía `value` + callbacks. Ver detalle en
[`COMPONENTS.md`](COMPONENTS.md).

### 3.3 Capa de modelo (`model/`)

- **Data classes inmutables** (`PersonalData`, `ContactData`) que
  representan el estado final de cada paso.
- **Enums** (`Sex`, `EducationLevel`) con un `labelRes` para resolver
  el texto traducido en cualquier idioma.
- **Objetos catálogo** (`LatinAmericaCountries`, `ColombianCities`)
  con la lista estática de opciones.
- **Extensiones** (`Sex.label()`, `EducationLevel.label()`) para
  resolver el texto traducido a partir del `Context`.
- **Helpers de validación** (`isEmailValid`, `isPhoneValid`) y
  formato (`formatDate`).

Ver detalle en [`MODEL.md`](MODEL.md).

### 3.4 Capa de tema (`ui/theme/`)

- **`Color.kt`**: paleta cruda (`Color(0xFF…)`) en `val` top-level.
- **`Dimens.kt`**: `object Spacing` (xs/sm/md/lg/xl) y `object Shapes`
  (radio de esquina). Centralizar medidas evita valores sueltos en
  cada pantalla.
- **`Type.kt`**: `Typography` de Material 3 con la escala que la app
  realmente usa.
- **`Theme.kt`**: `LabsTheme` (Composable raíz). Decide entre paleta
  clara/oscura/dinámica y tiñe la barra de estado. Ver
  [`THEME.md`](THEME.md).

---

## 4. Flujo de datos (por pantalla)

```
   ┌─────────────────────────────────────────────┐
   │            rememberSaveable                 │
   │   (sobrevive a rotación y cambio de tema)   │
   └─────────────────┬───────────────────────────┘
                     │
   usuario ─teclea─► │ State<T>  ─onValueChange─► validador
                     │                                       │
                     │                                       ▼
                     │                              errorMessage / isError
                     ▼                                       │
              Compose recompone ◄────────────────────────────┘
                     │
                     ▼
              botón "Siguiente/Finalizar"
                     │
                     ▼
              valida obligatorios ──falla──► marca errores in‑line
                     │
                     │ pasa
                     ▼
              construye data class (PersonalData / ContactData)
                     │
                     ▼
              callback onNext / onSubmit
                     │
                     ├─► Log.d(tag, data.toLog(ctx))   ─► Logcat
                     └─► startActivity(Intent)         ─► siguiente pantalla
```

Puntos clave:

- **Estado**: se guarda con `rememberSaveable`, no `remember`. Esto
  hace que Android serialice el valor en `Bundle` ante rotación o
  cambio de configuración, satisfaciendo el requisito del laboratorio.
- **Validación**: se ejecuta en el callback del botón (no en cada
  `onValueChange`). Mientras el usuario escribe, los errores se
  limpian automáticamente si el campo deja de estar vacío.
- **Logging**: cada `data class` tiene su propio `toLog(ctx)` que
  produce el texto exactamente con el formato pedido por el
  enunciado.

---

## 5. Decisiones de diseño

| Decisión                                            | Por qué                                                           |
|-----------------------------------------------------|-------------------------------------------------------------------|
| `ComponentActivity` + `setContent`                  | Es el patrón oficial moderno; reemplaza `AppCompatActivity`.      |
| Estado en el composable, sin ViewModel              | Alcance pequeño; añadir VM sería sobre‑ingeniería.                |
| `Intent` para navegar, no Navigation Compose        | El enunciado pide Activities nombradas y `setContent`.            |
| Tema con `dynamicColor = false` por defecto         | Mantiene la paleta teal coherente en Android 12+; ver `Theme.kt`. |
| Paleta teal/amber/indigo + escala de grises neutra  | Cumple Material 3 con buena jerarquía y contraste.                |
| 2 idiomas (es + en)                                 | Cubre el requisito mínimo de multilenguaje.                       |
| `rememberSaveable` con tipos primitivos/Strings     | `Bundle` los soporta directo; los enums se guardan por `name`.    |
| `OutlinedTextField` en lugar de `TextField`         | M3 moderno, menos ruido visual en formularios largos.            |
| `Card` con `RoundedCornerShape(Shapes.cardCorner)` | Reúne campos relacionados y da respiro visual.                    |
| `imePadding()` + `verticalScroll()`                 | Garantiza que el teclado no tape los campos (requisito).         |
| `keyboardOptions` con `ImeAction.Next`/`Done`       | Cumple "siguiente en vez de enter" del enunciado.                 |

---

## 6. Requisitos del laboratorio ↔ implementación

| Requisito                                                | Dónde se cumple                                          |
|----------------------------------------------------------|----------------------------------------------------------|
| Crear `PersonalDataActivity` con nombres*, apellidos*, sexo, fecha*, escolaridad | `PersonalDataScreen`                                       |
| Crear `ContactDataActivity` con tel*, dirección, email*, país*, ciudad | `ContactDataScreen`                                        |
| Teclado "siguiente" en lugar de "enter"                  | `KeyboardOptions(imeAction = ImeAction.Next/Done)`       |
| Teclado no tapa el campo                                | `Modifier.imePadding()` + `verticalScroll()`              |
| Persistir en cambios de configuración                   | `rememberSaveable`                                       |
| 2 idiomas                                                | `values/strings.xml` + `values-en/strings.xml`           |
| Ícono personalizado                                      | `drawable/ic_launcher_*` + `mipmap-*/ic_launcher.*`      |
| Validar obligatorios y loguear al pulsar Siguiente      | `onClick` del botón → `Log.d(tag, data.toLog(ctx))`      |
| Material Design, soporte Android 5.0+                    | Material 3 + `AppCompat` transitivo en `themes.xml`      |
| IDs en elementos XML                                     | Cada `OutlinedTextField` se identifica por label/estado; no usamos `findViewById` (Compose), pero los strings tienen `name` único |
