# Documentación del Lab1‑UI

> Documentación técnica del laboratorio. Cada archivo se enfoca en un
> aspecto concreto para que sea fácil de navegar.

## Índice

| Documento                              | Contenido                                                |
|----------------------------------------|----------------------------------------------------------|
| [`ARCHITECTURE.md`](ARCHITECTURE.md)   | Vista general del proyecto, capas, stack y decisiones de diseño. |
| [`DIAGRAMS.md`](DIAGRAMS.md)           | Plan de diagramas Mermaid (navegación, datos, validación, modelo, temas). |
| [`MODEL.md`](MODEL.md)                 | Capa de modelo: `PersonalData`, `ContactData`, enums, catálogos, validadores. |
| [`COMPONENTS.md`](COMPONENTS.md)       | Componentes reutilizables (`ui/components/`): API, comportamiento, estado interno. |
| [`SCREENS.md`](SCREENS.md)             | Activities y pantallas (`MainActivity`, `PersonalDataActivity`, `ContactDataActivity`). |
| [`THEME.md`](THEME.md)                 | Sistema de temas: paleta, espaciados, tipografía, `LabsTheme`. |
| [`PRESENTATION_SCRIPT.md`](PRESENTATION_SCRIPT.md) | Guion cronometrado para exponer las slides + demo en vivo + Q&A. |

## Cómo leer esta documentación

Si nunca has visto el proyecto, lee en este orden:

1. **`ARCHITECTURE.md`** — para entender la estructura y el flujo.
2. **`DIAGRAMS.md`** — para visualizar cómo se conectan las piezas.
3. **`SCREENS.md`** y **`COMPONENTS.md`** — para entender qué hace
   cada pantalla y cada Composable.
4. **`MODEL.md`** y **`THEME.md`** — para los detalles de tipos y
   estilos.

Si vas a **modificar** el código:

- Cambias un formulario → `SCREENS.md` + `COMPONENTS.md`.
- Cambias validación o formato de log → `MODEL.md`.
- Cambias colores, fuentes o tamaños → `THEME.md`.
- Cambias el flujo entre pantallas → `DIAGRAMS.md` (sección 1).

## Documentación en código

Además de estos documentos, **cada `fun`, `data class`, enum y objeto
del código tiene KDoc** con su contrato, parámetros y comportamiento
clave. La forma más rápida de verla es abrir cualquier archivo `.kt`
del módulo `Lab1-UI/src/main/java/co/edu/udea/compumovil/gr05_20262/lab1/`
en Android Studio (la doc aparece al hacer hover sobre el símbolo).

## Estado de la documentación

| Símbolo                         | Documentado en código | Documentado en docs |
|---------------------------------|-----------------------|---------------------|
| `MainActivity`, `WelcomeScreen` | ✅                     | ✅ `SCREENS.md`     |
| `PersonalDataActivity`, `PersonalDataScreen` | ✅          | ✅ `SCREENS.md`     |
| `ContactDataActivity`, `ContactDataScreen`   | ✅          | ✅ `SCREENS.md`     |
| `SectionTitle`, `StepProgress`, `SexSelector`, `EducationDropdown`, `DatePickerField`, `AutocompleteField` | ✅ | ✅ `COMPONENTS.md` |
| `PersonalData`, `ContactData`, validadores   | ✅          | ✅ `MODEL.md`       |
| `Sex`, `EducationLevel`, `Locations`          | ✅          | ✅ `MODEL.md`       |
| `LabsTheme`, paleta, tipografía               | ✅          | ✅ `THEME.md`       |
