# Guion de exposición — Lab1‑UI (3 presentadores)

> Versión del guion repartida entre **3 presentadores**, cada uno
> habla **una sola vez** (un solo bloque de slides).
>
> **Contexto:** este es el **Laboratorio 1** del curso de Computación
> Móvil. La guía del laboratorio pedía construir una app Android
> que capture datos personales y de contacto, valide los obligatorios,
> soporte 2 idiomas, persista en rotación y se entregue firmada como
> APK. Lo que se muestra en estas slides es **nuestra implementación
> del Lab1**, con un sistema de diseño propio (paleta, tipografía,
> espaciado y componentes) que va más allá del mínimo pedido.
>
> **Duración total:** ~8‑10 min exposición + 3‑5 min Q&A.
>
> **Reparto:**
>
> | Persona  | Bloque                          | Slides    | Tema                                |
> |----------|---------------------------------|-----------|-------------------------------------|
> | Persona 1| A — Contexto                    | 1, 2, 3   | Qué es Lab1 y cómo se navega        |
> | Persona 2| B — Sistema de diseño           | 4, 5, 6   | Decisiones de diseño y por qué      |
> | Persona 3| C — Demo + componentes + cierre | 7, 8      | Demo en vivo, cierre                |

---

## Antes de salir al frente (checklist compartido)

- [ ] Proyector con la app de slides abierta en **Slide 1**.
- [ ] APK firmado de Lab1‑UI instalado en un celular físico (o
  emulador con pantalla grande). **La Persona 3 hace la demo.**
- [ ] Android Studio abierto en la laptop del presentador con
  **Logcat visible** (filtro `tag:Lab1-UI`). **La Persona 3 lo muestra.**
- [ ] Idioma del dispositivo en **español**.
- [ ] Modo claro activo.
- [ ] Los 3 presentadores se ponen de acuerdo en **cómo pasar el
  control**: quién hace click a la siguiente slide, quién tiene el
  celular, quién tiene la laptop.
- [ ] Tener a mano `docs/` por si preguntan detalles técnicos.

---

## 🎤 Persona 1 — Bloque A: Contexto (Slides 1‑3)

> "Buenas, somos el grupo 05 de Computación Móvil. Hoy les
> presentamos el **Laboratorio 1**: construimos una app Android
> que captura datos personales y de contacto, valida los campos
> obligatorios, soporta dos idiomas y guarda el estado al rotar la
> pantalla. La entrega del laboratorio es esta APK firmada."

**Mientras habla:** mostrar slide 1, click a slide 2.

### Slide 1 — Portada

**En pantalla:** *"UI · Lab1‑UI · Rediseño visual de la interfaz
gráfica · Computación Móvil · Grupo Gr05 · Jetpack Compose"*

> "Esta es nuestra entrega del Lab1. La app está hecha 100% en
> Jetpack Compose y, además de cumplir lo que pide la guía, le
> pusimos un sistema de diseño propio para que se vea consistente
> y profesional de punta a punta."

**Tip:** no leer la slide completa.

**Duración:** ~20 s.

### Slide 2 — ¿Qué es Lab1‑UI?

**Click a slide 2.**

> "El Laboratorio 1 del curso pide construir una app que pida
> datos personales y de contacto en un formulario de dos pasos,
> valide los obligatorios y registre los datos en Logcat con un
> formato específico.
>
> Nuestra implementación está hecha **100% en Jetpack Compose** —
> declarativa, sin XMLs de layout —, soporta **español e inglés**,
> y guarda lo que el usuario escribió cuando rota la pantalla o
> cambia el idioma. Eso último lo conseguimos con `rememberSaveable`,
> que serializa el estado en el `Bundle` de Android."

**Duración:** ~45 s.

### Slide 3 — El flujo: 3 pantallas

**Click a slide 3.**

> "El flujo son tres pantallas en cadena. Primero, una bienvenida
> con una insignia y un botón 'Comenzar'. Después, los datos
> personales: nombres, apellidos, sexo, fecha de nacimiento y
> escolaridad. Y por último, los datos de contacto: teléfono,
> dirección, email, país y ciudad. Cada paso muestra un indicador
> 'Paso X de 2' para que el usuario sepa siempre dónde está
> parado.
>
> Cuando el usuario pulsa 'Siguiente' o 'Finalizar', validamos
> los obligatorios y, si todo está bien, escribimos en Logcat el
> bloque con el formato exacto que pide el enunciado del Lab."

**Transición:** "Ahora [nombre de la Persona 2] les va a contar
cómo decidimos darle una identidad visual propia."

**Duración total Persona 1:** ~2 min.

---

## 🎤 Persona 2 — Bloque B: Sistema de diseño (Slides 4‑6)

**Recibe el control.** Mientras habla, hacer click a slides 4 → 5 → 6.

### Slide 4 — Antes vs. después

**Click a slide 4.**

> "Cuando uno crea un *New Project* en Android Studio con Compose,
> lo que sale es la columna de la izquierda: el **morado genérico**
> del tema base, campos uno tras otro sin agrupación, sin indicio
> de progreso entre pantallas y una mezcla rara de tipografías
> porque solo tres estilos están definidos y el resto cae al
> default.
>
> Lo que decidimos hacer para Lab1 está en la columna derecha: una
> **paleta propia** con tres roles de color —teal como primario,
> ámbar como secundario, índigo como terciario—, los campos
> **agrupados en tarjetas** por tema, un **indicador 'Paso 1 de 2'**
> entre pantallas, y una **escala tipográfica y de espaciado
> consistente** en todo el proyecto."

**Apunte:** si el docente pregunta *¿por qué teal?*: *"porque
transmite confianza y orden, apropiado para un formulario de
datos personales, y se diferencia del morado genérico que tendría
cualquier app creada sin diseño."*

**Duración:** ~1 min 15 s.

### Slide 5 — Sistema de color

**Click a slide 5.**

> "Detengámonos en el color. En Material 3 cada color tiene un
> **rol**: no es solo 'se ve bien', es 'se usa acá'. El teal lo
> usamos para la barra superior, los botones principales y los
> títulos. El ámbar lo reservamos para acentos cálidos —las
> flechas del indicador de paso, por ejemplo—. Y el índigo queda
> como acento frío para detalles complementarios.
>
> Detrás declaramos **todos** los roles del estándar —no solo
> `primary`, también `primaryContainer`, `onPrimary`, `surface`,
> `surfaceVariant`, `outline` y la familia de `error`— para que
> cualquier componente reciba un color coherente con la paleta,
> en vez de heredar el valor por defecto.
>
> Si el sistema está en modo oscuro, `LabsTheme` elige
> automáticamente la variante oscura."

**Duración:** ~1 min 30 s.

### Slide 6 — Tipografía y espaciado

**Click a slide 6.**

> "Los colores solos no bastan; la tipografía y el espaciado son
> los que dan la sensación de orden. Definimos una escala
> tipográfica explícita —headline, title, body y label con sus
> tamaños, pesos y `lineHeight`— para que **todos** los estilos
> estén declarados.
>
> Y centralizamos el espaciado en un único `object Spacing` con
> cinco valores: 4, 8, 16, 24 y 32 dp. **Nunca** escribimos
> `16.dp` suelto en un composable: siempre viene de `Spacing.md`.
> Eso hace que ajustar el 'aire' de la app sea tocar un solo lugar."

**Apunte:** *"Un detalle: el ícono y los textos del botón 'Comenzar'
usan el mismo `Spacing.lg` (24 dp) que el padding entre las Cards,
así que el ritmo vertical se siente parejo aunque no te des
cuenta."*

**Transición:** "Listo el sistema de diseño. Ahora [nombre de la
Persona 3] les muestra cómo se siente todo esto en la pantalla."

**Duración total Persona 2:** ~4 min.

---

## 🎤 Persona 3 — Bloque C: Demo + componentes + cierre (Slides 7‑8 + demo)

**Recibe el control + el celular con la app abierta + la laptop con
Logcat visible.** Mientras habla, hacer click a slide 7.

### Slide 7 — Componentes: orden y progreso

**Click a slide 7.**

> "Hay dos decisiones de diseño que vale la pena resaltar porque
> son las que más se *sienten* al usar la app.
>
> Primero, **agrupar los campos en tarjetas por tema**. En datos
> personales hay dos: una para los básicos —nombres, apellidos,
> sexo— y otra para los adicionales —fecha, escolaridad—. Esto
> crea jerarquía: el ojo sabe dónde termina un bloque.
>
> Segundo, el **indicador 'Paso X de Y' con barra de progreso**.
> Es un composable pequeño —`StepProgress`— que recibe solo dos
> enteros. Convierte dos pantallas sueltas en un flujo perceptible.
> Si mañana crece a tres pasos, basta con pasarle `3` y `3`."

**Duración:** ~1 min.

### 🎬 Demo en vivo (~1 min 30 s)

> "Déjenme mostrarles cómo se siente todo esto en la pantalla y,
> de paso, los requisitos del Lab en acción."

**Pasos a ejecutar (marcados como coreografía, no se narran todos
los detalles al público):**

1. **Pantalla 1 (Bienvenida).** Mostrar insignia + textos + botón.
   *Decir:* "tres niveles de texto con sus estilos; la insignia
   ya no flota, va dentro de una superficie circular del color de
   marca."

2. **Tap 'Comenzar' → Pantalla 2.** *Decir:* "acá está el
   'Paso 1 de 2' y las dos tarjetas."

3. **Escribir "Pepito" en nombres, dejar apellidos en blanco, tap
   'Siguiente'.** *Decir:* "validación en línea: campo vacío se
   marca en rojo, que es lo que pide el Lab."

4. **Rellenar apellidos, fecha con el DatePicker, sexo y
   escolaridad. Tap 'Siguiente'.** *Decir:* "el DatePickerDialog
   se abre con el icono de calendario."

5. **Pantalla 3.** *Decir:* "Paso 2 de 2, autocompletes filtrando
   en vivo —el Lab pedía autocomplete con los países de
   Latinoamérica y ciudades de Colombia."

6. **Email mal, tap 'Finalizar'.** *Decir:* "el matcher de
   `Patterns.EMAIL_ADDRESS` rechaza el formato."

7. **Email bien, país, ciudad, tap 'Finalizar'.** *Decir:* "se
   muestra el `AlertDialog` y, lo más importante, los datos
   quedaron en Logcat con el formato exacto del enunciado. [Abrir
   Logcat] Miren, acá está el bloque 'Información personal'."

8. **Rotar a landscape.** *Decir:* "el estado se preserva —que
   es otro requisito del Lab— y el layout se reorganiza: nombres
   y apellidos lado a lado, botón reubicado."

9. **Cambiar idioma del sistema a inglés.** *Decir:* "todos los
   textos cambian al instante y el estado de los campos se
   preserva."

**Duración:** ~1 min 30 s.

### Slide 8 — Cierre

**Click a slide 8.**

> "Para cerrar: lo que pidió el Laboratorio 1 está **todo**
> cubierto —el formulario con los campos obligatorios, la
> validación, el log en Logcat con el formato exacto, el
> multilenguaje, la persistencia en rotación, el ícono
> personalizado y el APK firmado—. Lo que añadimos por nuestra
> cuenta fue una **identidad visual**: una paleta con roles
> claros, una escala tipográfica consistente, un espaciado
> centralizado y unos componentes reutilizables.
>
> Eso es lo que significa 'un sistema de diseño': no pintar tres
> cosas bonitas, sino que cada decisión se pueda cambiar en
> **un solo lugar**."

**Pausa. Mirada al público.**

> "Muchas gracias. ¿Preguntas?"

**Duración total Persona 3:** ~3 min 30 s.

---

## 🎤 Q&A (entre los 3)

Cada quien contesta lo que sabe. Reparto sugerido para no pisar:

| Tema                                 | Quien responde |
|--------------------------------------|----------------|
| Funcionalidad / flujo / validación   | Persona 1      |
| Color / tipografía / tema oscuro     | Persona 2      |
| Componentes / demo / estado al rotar | Persona 3      |
| Multilenguaje                        | Cualquiera     |
| ViewModel / Navigation / arquitectura| Persona 2 o 3  |

### Preguntas probables y respuestas (cheat sheet)

#### 1. *"¿Por qué no usaron ViewModel?"*
> "Por alcance. El Lab 1 no lo pedía y el formulario tiene estado
> puramente local. Usar `rememberSaveable` ya cumple el requisito de
> persistir en rotación, y nos ahorra una capa de indirección. Si la
> app creciera —por ejemplo, enviando datos a un backend— ahí sí
> migraríamos a ViewModel."

#### 2. *"¿Por qué teal y no otro color?"*
> "Porque transmite confianza y orden, apropiado para un formulario
> de datos personales. Además, el verde‑azulado es suficientemente
> distintivo del morado genérico de Android Studio, que es justo el
> problema que estamos atacando."

#### 3. *"¿Y el modo oscuro?"*
> "Está implementado. `LabsTheme` mira `isSystemInDarkTheme()` y
> elige entre `LightColorScheme` y `DarkColorScheme`. Cada color
> tiene su variante oscura en `Color.kt`. La status bar se tiñe del
> color primario y los iconos cambian automáticamente."

#### 4. *"¿Cómo manejan el multilenguaje?"*
> "Todos los textos vienen de `strings.xml`. Tenemos
> `values/strings.xml` en español y `values-en/strings.xml` en
> inglés. El idioma se elige del sistema. En el código usamos
> `stringResource(id = R.string.…)` y `context.getString(...)`.
> Para los enums (`Sex`, `EducationLevel`) seguimos el mismo
> patrón: cada valor tiene un `labelRes`."

#### 5. *"¿Qué pasa con el teclado tapando los campos?"*
> "La columna raíz usa `Modifier.imePadding()` para empujar el
> contenido hacia arriba cuando aparece el teclado. Combinado con
> `verticalScroll(...)`, el usuario siempre puede scrollear para
> llegar al campo que está editando. Es uno de los requisitos del
> Lab."

#### 6. *"¿Cómo cambia el layout en landscape?"*
> "Con `LocalConfiguration.current.orientation` detectamos si está
> en landscape. Si lo está, ciertas Cards se reorganizan: los dos
> `OutlinedTextField` de nombres/apellidos van lado a lado, la
> fecha y el dropdown se acotan a 280 dp, y el botón 'Siguiente'
> se reubica. El estado se preserva porque sigue siendo
> `rememberSaveable`."

#### 7. *"¿Y los Logs?"*
> "Cada data class tiene su propio `toLog(context)` que arma el
> bloque exactamente con el formato del enunciado del Lab. La
> `Activity` hace `Log.d(R.string.log_tag, data.toLog(this))` al
> pulsar Siguiente / Finalizar. El tag es localizable para que
> también cambie con el idioma."

#### 8. *"¿Cómo agregaron el ícono personalizado?"*
> "Reemplazamos los `mipmap-*` por defecto con un ícono propio y
> referenciamos `@mipmap/ic_launcher` y `@mipmap/ic_launcher_round`
> en el `AndroidManifest`."

#### 9. *"¿Por qué no Navigation Compose?"*
> "El Lab pedía Activities nombradas (`PersonalDataActivity`,
> `ContactDataActivity`) y `setContent { … }`. Usar Navigation
> Compose habría significado alejarse del patrón pedido. Con tres
> pantallas y un flujo lineal, `Intent` explícito es más simple y
> 100% compatible."

#### 10. *"¿Qué pasa si en el futuro se quiere agregar un tercer paso?"*
> "Es un cambio chico. `StepProgress` ya recibe `totalSteps` como
> parámetro, así que bastaría con pasarle `3`. El nuevo paso
> seguiría el mismo patrón: nueva `Activity`, nuevo composable de
> pantalla, mismo tema y misma escala."

---

## Cierre del Q&A

> (Persona 3, ya que es quien tiene la laptop)

> "Si quedó alguna duda, toda la documentación está en `docs/`:
> arquitectura, diagramas Mermaid, descripción de cada componente
> y de cada modelo. Y por supuesto el código fuente, que muestra
> cómo se tomó cada decisión. El repositorio está en
> github.com/AtenciaMaria/LabsCM20262-Gr05."

---

## Notas finales

- **No leer los bullets.** La slide los muestra, el presentador
  los explica.
- **Si se pasan del tiempo**, pueden comprimir la demo a 3 pasos
  clave: validación en línea → Logcat → rotación. Eso siempre
  impresiona.
- **Si se quedan cortos**, abrir la pregunta *"¿Por qué no
  ViewModel?"* (Persona 1) y *"¿Cómo cambia en landscape?"*
  (Persona 3) que son las que más curiosidad generan.
- **Al terminar**, dejar la app abierta en la pantalla de
  bienvenida para que la gente se acerque a probar.
- **Quién hace click a la siguiente slide**: definir antes de
  subir. Sugerencia: la Persona que está hablando NO hace click;
  lo hace la Persona que viene.
