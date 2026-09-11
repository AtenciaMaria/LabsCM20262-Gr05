# Plan de diagramas

> Los diagramas están escritos en **Mermaid** y se renderizan
> automáticamente en GitHub, GitLab, VS Code (con la extensión
> *Markdown Preview Mermaid Support*) y Android Studio (con el plugin
> *Mermaid*).

---

## Índice de diagramas

| # | Diagrama                       | Qué responde                                                |
|---|--------------------------------|-------------------------------------------------------------|
| 1 | Navegación entre Activities    | ¿A qué pantalla va el usuario y desde dónde?                |
| 2 | Flujo de entrada de datos      | ¿Cómo viaja lo que escribe el usuario hasta Logcat?         |
| 3 | Árbol de componentes UI        | ¿Qué Composables componen cada pantalla?                    |
| 4 | Estados de validación          | ¿Cuándo aparece y desaparece cada error?                    |
| 5 | Mapa de capas                  | ¿Quién depende de quién?                                    |
| 6 | Modelo de datos                | ¿Qué campos tiene cada `data class`?                        |
| 7 | Sistema de temas               | ¿Cómo se eligen colores, tipografía y formas?               |

---

## 1. Navegación entre Activities

```mermaid
flowchart LR
    Launcher((Launcher icon)) --> Main
    Main["MainActivity<br/>WelcomeScreen"] -- "botón 'Comenzar'</br>Intent explícito" --> Personal
    Personal["PersonalDataActivity<br/>PersonalDataScreen"] -- "botón 'Siguiente'<br/>(validación OK)" --> Contact
    Contact["ContactDataActivity<br/>ContactDataScreen"] -- "botón 'Finalizar'<br/>(validación OK)" --> Dialog["AlertDialog<br/>confirmación"]
    Dialog -- "Entendido" --> Contact
```

**Notas**

- No hay back‑stack personalizado; cada Activity se abre con
  `startActivity(Intent)` estándar.
- El botón *Atrás* del sistema regresa siempre a la pantalla previa
  sin perder estado (gracias a `rememberSaveable`).

---

## 2. Flujo de datos (entrada → Logcat)

```mermaid
sequenceDiagram
    autonumber
    actor U as Usuario
    participant TF as OutlinedTextField
    participant S as rememberSaveable<T>
    participant V as Validador
    participant B as Button onClick
    participant M as data class<br/>(PersonalData / ContactData)
    participant L as Logcat
    participant N as Siguiente Activity

    U->>TF: escribe texto
    TF->>S: onValueChange(new)
    S-->>TF: Compose recompone
    Note over S: si el campo deja<br/>de estar vacío,<br/>se limpia su error
    U->>B: tap "Siguiente/Finalizar"
    B->>V: validar obligatorios
    alt algún campo obligatorio vacío
        V-->>TF: isError = true<br/>supportingText = msg
    else todo OK
        V->>M: construir instancia
        M-->>B: data válida
        B->>L: Log.d(tag, data.toLog(ctx))
        B->>N: startActivity(Intent)
    end
```

---

## 3. Árbol de componentes de cada pantalla

### Welcome

```mermaid
graph TD
    A[MainActivity.setContent] --> B[LabsTheme]
    B --> C[Scaffold]
    C --> D[WelcomeScreen]
    D --> D1[Surface · badge circular]
    D1 --> D2[Icon · PersonAdd]
    D --> D3[Text · welcome_title]
    D --> D4[Text · welcome_subtitle]
    D --> D5[Text · welcome_description]
    D --> D6[Button · "Comenzar"]
```

### PersonalData

```mermaid
graph TD
    A[PersonalDataActivity] --> B[LabsTheme]
    B --> C[Scaffold]
    C --> CTB[PersonalTopBar]
    C --> SCR[PersonalDataScreen]
    SCR --> S1[SectionTitle]
    SCR --> S2[StepProgress · 1/2]
    SCR --> K1[Card · "Datos básicos"]
    K1 --> K1a[OutlinedTextField · nombres]
    K1 --> K1b[OutlinedTextField · apellidos]
    K1 --> K1c[SexSelector]
    SCR --> K2[Card · "Datos adicionales"]
    K2 --> K2a[DatePickerField]
    K2 --> K2b[EducationDropdown]
    SCR --> BTN[Button · "Siguiente"]
```

### ContactData

```mermaid
graph TD
    A[ContactDataActivity] --> B[LabsTheme]
    B --> C[Scaffold]
    C --> CTB[ContactTopBar]
    C --> SCR[ContactDataScreen]
    SCR --> S1[SectionTitle]
    SCR --> S2[StepProgress · 2/2]
    SCR --> K1[Card · "Cómo contactarte"]
    K1 --> K1a[OutlinedTextField · teléfono]
    K1 --> K1b[OutlinedTextField · dirección]
    K1 --> K1c[OutlinedTextField · email]
    SCR --> K2[Card · "Ubicación"]
    K2 --> K2a[AutocompleteField · país]
    K2 --> K2b[AutocompleteField · ciudad]
    SCR --> BTN[Button · "Finalizar"]
    SCR --> DLG[AlertDialog · confirmación]
```

---

## 4. Máquina de estados de validación

```mermaid
stateDiagram-v2
    [*] --> Vacio
    Vacio --> Escribiendo : usuario empieza a tipear
    Escribiendo --> Vacio : texto se borra
    Escribiendo --> Valido : texto no vacío
    Escribiendo --> ValidoFecha : fecha seleccionada
    Valido --> Valido : más cambios (sigue válido)
    Valido --> Pendiente : botón presionado<br/>con otro campo vacío
    Pendiente --> Escribiendo : usuario corrige
    Pendiente --> Listo : todos los obligatorios OK
    Listo --> [*] : Log + Intent
```

**Reglas por campo**

| Campo                | Tipo de validación             | Mensaje (`error_*`)             |
|----------------------|--------------------------------|---------------------------------|
| Nombres              | `isNotBlank()`                 | `R.string.error_required`       |
| Apellidos            | `isNotBlank()`                 | `R.string.error_required`       |
| Fecha de nacimiento  | `!= null`                      | `R.string.error_required`       |
| Teléfono             | `isPhoneValid()` (7–15 dígitos/+/‑/() ) | `R.string.error_invalid_phone` |
| Email                | `isEmailValid()` (Patterns.EMAIL_ADDRESS) | `R.string.error_invalid_email` |
| País                 | `isNotBlank()`                 | `R.string.error_no_country`     |
| Dirección            | opcional                       | —                               |
| Ciudad               | opcional                       | —                               |
| Sexo, Escolaridad    | opcional                       | —                               |

---

## 5. Mapa de dependencias entre capas

```mermaid
graph LR
    subgraph manifest
        AM[AndroidManifest]
    end
    subgraph activities
        MA[MainActivity]
        PA[PersonalDataActivity]
        CA[ContactDataActivity]
    end
    subgraph components
        ST[SectionTitle]
        SP[StepProgress]
        SS[SexSelector]
        ED[EducationDropdown]
        DP[DatePickerField]
        AC[AutocompleteField]
    end
    subgraph model
        PD[PersonalData]
        CD[ContactData]
        SX[Sex]
        EL[EducationLevel]
        LAC[LatinAmericaCountries]
        CC[ColombianCities]
    end
    subgraph theme
        LT[LabsTheme]
        TY[Typography]
        CO[Color palette]
        SP2[Spacing/Shapes]
    end
    subgraph res
        STR[values/strings.xml + values-en/strings.xml]
    end

    AM --> MA & PA & CA
    MA --> LT
    PA --> LT & ST & SP & SS & ED & DP
    CA --> LT & ST & SP & AC

    SS --> SX
    ED --> EL
    AC --> LAC & CC
    DP --> PD
    PA --> PD & SX & EL
    CA --> CD & LAC & CC

    LT --> TY & CO
    components --> SP2
    activities --> STR
    components --> STR
    SX & EL --> STR
```

Reglas:

- **activities → components** ✅ permitido (las pantallas usan los
  componentes).
- **components → model** ✅ permitido solo si el componente es
  genérico y necesita conocer el enum (e.g. `SexSelector`, `EducationDropdown`,
  `DatePickerField`).
- **model → ui** ❌ **prohibido**: el modelo nunca importa Compose.
- **theme** es transversal: lo importan las activities y los
  componentes, pero no depende de nadie.

---

## 6. Modelo de datos

```mermaid
classDiagram
    class PersonalData {
        +String names
        +String surnames
        +Sex? sex
        +Long? birthDateMillis
        +EducationLevel? education
        +isValid() Boolean
        +toLog(Context) String
    }
    class ContactData {
        +String phone
        +String address
        +String email
        +String country
        +String city
        +isValid() Boolean
        +toLog(Context) String
    }
    class Sex {
        <<enum>>
        MALE
        FEMALE
        OTHER
        +labelRes: Int
        +fromKey(String?) Sex?
    }
    class EducationLevel {
        <<enum>>
        PRIMARY
        SECONDARY
        UNDERGRADUATE
        POSTGRADUATE
        +labelRes: Int
        +fromKey(String?) EducationLevel?
    }
    class LatinAmericaCountries {
        <<object>>
        +all: List~String~
    }
    class ColombianCities {
        <<object>>
        +all: List~String~
    }

    PersonalData --> Sex
    PersonalData --> EducationLevel
    ContactData ..> LatinAmericaCountries : opciones de país
    ContactData ..> ColombianCities : opciones de ciudad
```

**Persistencia**

- Mientras la Activity está viva: `rememberSaveable` (sobrevive a
  rotación, cambio de tema y de idioma).
- Al terminar la Activity (back del sistema, finish): se pierde.
  *No se persiste en disco* porque no es requisito.

---

## 7. Sistema de temas

```mermaid
flowchart TD
    ROOT[LabsTheme] --> DEC{¿dynamicColor<br/>y SDK ≥ 31?}
    DEC -- sí --> DYN{¿isSystemInDarkTheme?}
    DYN -- sí --> DD[dynamicDarkColorScheme]
    DYN -- no  --> DL[dynamicLightColorScheme]
    DEC -- no  --> SD{¿isSystemInDarkTheme?}
    SD -- sí --> DKD[DarkColorScheme]
    SD -- no  --> LKD[LightColorScheme]

    LKD --> P1[primary=TealPrimary]
    LKD --> P2[secondary=AmberSecondary]
    LKD --> P3[tertiary=IndigoTertiary]
    LKD --> P4[surface=LightSurface<br/>background=LightBackground]

    DKD --> Q1[primary=TealPrimaryDark]
    DKD --> Q2[secondary=AmberSecondaryDark]
    DKD --> Q3[tertiary=IndigoTertiaryDark]
    DKD --> Q4[surface=DarkSurface<br/>background=DarkBackground]

    ROOT --> TY[Typography · Type.kt]
    ROOT --> SP[Spacing/Shapes · Dimens.kt]
    ROOT --> SF[SideEffect: statusBarColor = primary]
```

**Roles M3 usados**

- `primary` / `onPrimary` → barra superior y FAB‑style.
- `primaryContainer` / `onPrimaryContainer` → badge de la pantalla de
  inicio.
- `surface` / `onSurface` → fondo de Cards y texto por defecto.
- `surfaceVariant` / `onSurfaceVariant` → textos secundarios y track
  de la `LinearProgressIndicator`.
- `error` / `onError` → borde rojo y texto de error en los campos
  obligatorios.

---

## Cómo generar los diagramas externamente

Si necesitas exportarlos a PNG/SVG (por ejemplo para el informe
escrito), puedes:

1. Copiar el bloque Mermaid y pegarlo en
   <https://mermaid.live> → *Export → PNG/SVG*.
2. O usar la CLI:
   ```bash
   npm i -g @mermaid-js/mermaid-cli
   mmdc -i docs/DIAGRAMS.md -o diagrams/ -t neutral
   ```

Para versiones impresas del laboratorio, los diagramas **1**, **4** y
**5** son los más útiles (navegación, validación y dependencias).
