# DevEx + Developer Productivity

## Autores

- Juan David Rodriguez Rodriguez
- Jesus Alberto Jauregui Conde
- David Santiago Castro Sierra

---

## Contenido

1. Introducción y objetivo de la entrega
2. Contexto del proyecto y estado actual del repo
3. Marco conceptual
4. Metodología de análisis
5. Diagnóstico DevEx del proyecto
6. Diagnóstico SPACE del proyecto
7. Puntos positivos
8. Puntos negativos
9. Oportunidades de mejora priorizadas
10. Métricas propuestas y cómo capturarlas
11. Conclusiones

---

## 1) Introducción y objetivo de la entrega

El propósito de esta entrega es analizar el proyecto `customer-management-system` desde dos marcos complementarios vistos en clase:

- **Developer Experience (DevEx)**, entendido como la calidad percibida por los desarrolladores al interactuar con herramientas, plataformas, procesos, código y cultura de trabajo.
- **Developer Productivity**, aterrizado mediante el framework **SPACE**, que evita reducir la productividad a una sola métrica y propone una lectura multidimensional del trabajo de ingeniería.

El objetivo no es medir al equipo con un indicador único ni emitir juicios simplistas sobre “productividad alta” o “productividad baja”. El objetivo es identificar, con evidencia concreta del repositorio y de los entregables previos, qué fricciones existen hoy para desarrollar sobre este sistema, qué prácticas ya fortalecen la experiencia del desarrollador y qué oportunidades de mejora deberían priorizarse en el corto, mediano y largo plazo.

---

## 2) Contexto del proyecto y estado actual del repo

### 2.1 Contexto técnico

El proyecto analizado es una aplicación Java basada en:

- Spring Boot
- JSF / PrimeFaces / JoinFaces
- Spring Data JPA
- Spring Security
- Maven
- PostgreSQL o MySQL, según configuración

La estructura del repositorio refleja una arquitectura clásica por capas, pero con una mezcla importante entre responsabilidades web, vista, seguridad y acceso a datos.

### 2.2 Evidencia objetiva observada

La revisión directa del repositorio permite establecer la siguiente línea base:

| Indicador | Evidencia actual | Lectura |
| :-- | :-- | :-- |
| Archivos Java en `src/main/java` | 67 | Tamaño moderado para un proyecto académico con varias capas |
| Archivos Java en `src/test/java` | 16 | Hay avance real frente al estado inicial reportado en el README |
| Archivos XHTML | 17 | Existe una capa de UI no trivial que aumenta el costo de mantenimiento |
| Workflow CI | `.github/workflows/build.yml` | Hay automatización de `mvn verify` + SonarQube en `push` y `pull_request` |
| Cobertura configurada | JaCoCo en `pom.xml` | Se definieron umbrales de 55% líneas y 10% ramas |
| Tests en README | El README todavía afirma que no hay tests | Hay desalineación entre documentación y estado real del proyecto |
| Setup del proyecto | DB y scripts SQL manuales | El onboarding técnico sigue teniendo alta fricción |

### 2.3 Evidencia histórica del curso

El repositorio ya contiene tres entregas previas que funcionan como trazabilidad del proceso:

- `docs/DEUDA_TECNICA_Y_REFACTORIZACION.md`
- `docs/CODIGO_LIMPIO_+_PRACTICAS_XP.md`
- `docs/PRIMERA_ENTREGA_2026.md`

Además, el historial Git evidencia actividad concreta durante el curso:

- 2026-02-19: creación del repositorio de la organización y entregable de Clean Code + XP.
- 2026-03-11 a 2026-03-13: configuración de SonarQube y CI.
- 2026-03-15: incorporación de pruebas automatizadas y JaCoCo.
- 2026-03-22: merges y ajustes posteriores sobre entregables previos.

### 2.4 Lectura general del estado actual

El proyecto no está en un estado “verde” desde la perspectiva de experiencia del desarrollador. Sin embargo, sí muestra una evolución importante durante el curso:

- pasó de un proyecto sin pruebas declaradas a uno con 16 clases de test;
- incorporó integración continua con análisis SonarQube;
- dejó evidencia documental de deuda técnica, calidad y prácticas XP;
- pero todavía conserva fricciones estructurales de onboarding, seguridad, mezcla de frameworks y documentación obsoleta.

En otras palabras, el equipo ya mejoró parte de la productividad habilitadora, pero la experiencia de desarrollo aún está condicionada por deuda técnica y por un setup local costoso.

---

## 3) Marco conceptual

### 3.1 DevEx

Con base en el material del curso y en el artículo **“DevEx: What Actually Drives Productivity”**, la experiencia del desarrollador puede analizarse desde tres dimensiones centrales:

#### a) Feedback loops

Se refiere a la velocidad y calidad de las respuestas que recibe el desarrollador al hacer cambios: compilación, ejecución de pruebas, validación local, revisiones, integración y despliegue.

#### b) Cognitive load

Se refiere a la carga mental necesaria para entender el sistema, navegar el código, usar herramientas, interpretar documentación y resolver tareas sin fricción innecesaria.

#### c) Flow state

Se refiere a la capacidad de trabajar con foco sostenido, poca interrupción, objetivos claros y baja fricción operativa.

Estas dimensiones son especialmente útiles porque no reducen DevEx a herramientas: también incluyen claridad, autonomía, deuda técnica, interrupciones, ambigüedad y cultura de trabajo.

### 3.2 SPACE

Con base en el material del curso y en el artículo **“The SPACE of Developer Productivity”**, la productividad del desarrollador debe analizarse como una constelación de dimensiones en tensión:

- **Satisfaction and well-being**
- **Performance**
- **Activity**
- **Communication and collaboration**
- **Efficiency and flow**

El punto central del framework es que **la productividad no puede medirse con una sola métrica**. Un aumento en actividad no implica necesariamente mejores resultados, mejor calidad ni mejor experiencia del equipo. Por eso SPACE obliga a balancear métricas técnicas, resultados, colaboración, bienestar y eficiencia.

---

## 4) Metodología de análisis

### 4.1 Principio de análisis

Esta entrega no utiliza una métrica única. Se adopta un enfoque mixto que combina:

- **evidencia objetiva del repositorio**;
- **evidencia externa** ya incorporada en el trabajo del curso, especialmente SonarQube y GitHub Actions;
- **instrumentos perceptuales propuestos**, mediante una mini encuesta diseñada para futuras mediciones.

### 4.2 Fuentes concretas utilizadas

Las fuentes analizadas fueron:

- `README.md`
- `pom.xml`
- `src/main/resources/application.properties`
- `.github/workflows/build.yml`
- `docs/DEUDA_TECNICA_Y_REFACTORIZACION.md`
- `docs/CODIGO_LIMPIO_+_PRACTICAS_XP.md`
- `docs/PRIMERA_ENTREGA_2026.md`
- historial Git del repositorio
- clases representativas como:
  - `AbstractController`
  - `DashboardViewController`
  - `SecurityConfig`
  - `LoginComponent`

### 4.3 Criterios de lectura

Cada hallazgo se interpretó así:

- si afecta tiempos de espera, validación o retroalimentación, impacta **feedback loops**;
- si aumenta complejidad accidental o ambigüedad, impacta **cognitive load**;
- si obliga a trabajo reactivo, interrupciones o retrabajo, impacta **flow state**;
- y luego se mapea a una o varias dimensiones de **SPACE**.

### 4.4 Importante: límites del análisis

No se aplicó todavía una encuesta respondida por el equipo, por lo que la dimensión perceptual de satisfacción y bienestar no puede afirmarse con datos observados. Por esa razón, esta entrega:

- **no infiere bienestar desde commits**;
- **no concluye motivación a partir de actividad técnica**;
- y deja una **plantilla de encuesta lista** las respuestas a esta encuesta se encuentran en la carpeta [Respuestas Plantilla](./RespuestasPlantilla)

---

## 5) Diagnóstico DevEx del proyecto

### 5.1 Feedback loops

#### Evidencias

- Existe CI en `.github/workflows/build.yml` con ejecución de `mvn -B verify` y análisis SonarQube en `push` a `main` y en `pull_request`.
- En `pom.xml` se configuró JaCoCo con umbrales mínimos de cobertura.
- Hay 16 clases de prueba bajo `src/test/java`, lo cual mejora la validación frente al estado declarado inicialmente.
- El README todavía indica “This project does not have any kind of tests :)”, lo cual contradice el estado actual.
- El setup local sigue requiriendo creación manual de base de datos y carga manual de múltiples scripts SQL.

#### Lectura

La experiencia del equipo en feedback loops es **mixta**.

Hay una mejora clara porque ya existen:

- pruebas automatizadas;
- verificación automática en CI;
- análisis estático con SonarQube.

Eso reduce el tiempo para detectar defectos y da señales rápidas sobre calidad y cobertura.

Sin embargo, persisten fricciones importantes:

- el onboarding técnico depende de pasos manuales;
- la documentación principal no refleja el estado real del proyecto;
- el flujo local de validación no parece estandarizado;
- y la dependencia de configuración de base de datos eleva el tiempo hasta lograr una ejecución exitosa.

#### Juicio DevEx

**Feedback loops: parcialmente favorables, pero todavía frágiles y desalineados.**  
La organización del curso avanzó en automatización, pero la experiencia diaria de levantar, validar y entender el proyecto aún es más lenta de lo deseable.

### 5.2 Cognitive load

#### Evidencias

- `AbstractController` mezcla lógica REST, navegación JSF, utilidades genéricas, reflexión e inicialización de estado.
- `DashboardViewController` mezcla REST, JSF, armado de modelos de UI, acceso a datos por query nativa y lógica de visualización.
- `SecurityConfig` usa `WebSecurityConfigurerAdapter`, enfoque legacy y `csrf().disable()`.
- `LoginComponent` contiene una validación hardcodeada para `admin`.
- En el árbol principal se observan patrones repetidos:
  - 11 usos de `@Autowired` por campo;
  - 18 usos de `@RequestMapping`;
  - 3 usos de `@CrossOrigin`;
  - 3 usos de `@ManagedBean`;
  - 1 uso de `createNativeQuery`;
  - 1 uso de reflexión con `newInstance()`.
- Las entregas previas ya documentaron deuda técnica, mezcla de frameworks, smells de arquitectura y problemas de seguridad.

#### Lectura

La carga cognitiva del proyecto es **alta**.

El desarrollador debe entender simultáneamente:

- Spring Boot;
- JSF/PrimeFaces;
- convenciones de controladores REST;
- navegación por vistas XHTML;
- configuración de seguridad legacy;
- y consultas SQL nativas embebidas en controladores.

Ese contexto multiplica la complejidad accidental. La mezcla de responsabilidades obliga a reconstruir mentalmente demasiadas capas para ejecutar una tarea aparentemente simple. También aumenta la dificultad de onboarding, debugging y refactorización.

La desalineación entre documentación y realidad técnica empeora el problema: cuando el README afirma que no hay pruebas pero el `pom.xml` sí contiene JaCoCo y existe `src/test`, el desarrollador tiene que invertir esfuerzo extra solo para saber cuál es la verdad del sistema.

#### Juicio DevEx

**Cognitive load: alto y claramente negativo para la experiencia de desarrollo.**  
Este es uno de los principales puntos de fricción del proyecto.

### 5.3 Flow state

#### Evidencias

- El setup local no es inmediato ni reproducible con un solo comando.
- Persisten problemas estructurales ya reportados por SonarQube: seguridad E, confiabilidad D, 75 issues y 7 hotspots en la línea base documentada.
- Varias clases base concentran deuda técnica, especialmente `AbstractController` y `AbstractService`, según la primera entrega.
- La mezcla de frameworks y responsabilidades favorece trabajo reactivo y retrabajo.
- Los cambios del curso muestran que parte del trabajo reciente se dedicó a “Potential fix for pull request finding”, lo que sugiere iteraciones reactivas sobre calidad.

#### Lectura

El flujo de trabajo del desarrollador se ve interrumpido por tres fuentes:

- deuda técnica estructural;
- claridad insuficiente del sistema;
- fricción operativa para validar cambios.

Aunque el proyecto ya tiene CI y pruebas, el costo mental para modificar el sistema sigue siendo alto. Un desarrollador puede entrar en “modo ejecución”, pero con facilidad saldrá de ese estado cuando tenga que:

- entender una clase base demasiado genérica;
- navegar entre JSF y Spring;
- corregir problemas heredados de seguridad;
- o resolver inconsistencias entre documentación y código.

#### Juicio DevEx

**Flow state: limitado.**  
Hay progreso en prácticas de ingeniería, pero todavía no existen condiciones sólidas para trabajo sostenido con foco y baja interrupción.

---

## 6) Diagnóstico SPACE del proyecto

### 6.1 Satisfaction and well-being

#### Qué se puede afirmar

No hay datos perceptuales directos del equipo todavía. Sería incorrecto inferir satisfacción, agotamiento o engagement únicamente a partir del repositorio.

#### Qué sí sugiere la evidencia

El proyecto contiene señales que suelen impactar negativamente esta dimensión:

- onboarding costoso;
- carga cognitiva alta;
- documentación desactualizada;
- deuda técnica concentrada;
- y trabajo correctivo recurrente.

A la vez, existen señales potencialmente positivas:

- avances visibles durante el curso;
- mayor automatización;
- trazabilidad de entregables;
- y existencia de CI, lo cual suele mejorar percepción de control y confianza.

#### Juicio SPACE

**Dimensión no medible todavía con rigor.**  
Debe instrumentarse con encuesta breve, porque esta es la dimensión menos confiable cuando se intenta deducir solo desde artefactos técnicos.

### 6.2 Performance

#### Evidencias

- La línea base de SonarQube reportada en la primera entrega muestra seguridad E, confiabilidad D y 75 issues.
- Existen vulnerabilidades y hotspots relacionados con seguridad, CSRF, credenciales y exposición de entidades.
- El proyecto ahora cuenta con pruebas y cobertura configurada, lo cual mejora la capacidad de sostener cambios.
- Persisten elementos que afectan calidad del resultado:
  - credenciales en texto plano en `application.properties`;
  - lógica de autenticación hardcodeada en `LoginComponent`;
  - consultas nativas ejecutadas desde `DashboardViewController`.

#### Lectura

La dimensión de performance, entendida como resultados y calidad del sistema, es **mixta pero con deuda importante**.

El equipo produjo valor tangible durante el curso:

- análisis de calidad;
- incorporación de tests;
- automatización CI;
- mejora de trazabilidad documental.

No obstante, el producto todavía arrastra riesgos técnicos severos que afectan la confiabilidad y la seguridad del software entregado.

#### Juicio SPACE

**Performance: mejorando, pero aún condicionado por deuda técnica y riesgo de calidad.**

### 6.3 Activity

#### Evidencias

- El historial Git muestra actividad continua entre febrero y marzo de 2026.
- Se observan commits de documentación, configuración Sonar, CI, pruebas y merges de pull requests.
- El repositorio evidencia trabajo en entregables, automatización y correcciones.

#### Lectura

La dimensión de actividad es **visible y medible**, pero debe interpretarse con cuidado.

El repositorio muestra que hubo actividad sostenida, pero SPACE advierte correctamente que actividad no equivale a productividad por sí sola. Más commits o más merges no implican necesariamente mejor experiencia ni mayor valor si gran parte del esfuerzo se consume en corregir fricción estructural.

#### Juicio SPACE

**Activity: positiva como señal de movimiento, insuficiente como métrica aislada.**

### 6.4 Communication and collaboration

#### Evidencias

- Existe `CONTRIBUTING.md`.
- Hay workflow de `pull_request` en GitHub Actions.
- El historial incluye merges de PR y arreglos posteriores.
- `RefactDone_CSDT-2026.md` actúa como bitácora de entregables.
- La documentación del curso está centralizada en `docs/`.

#### Lectura

La colaboración del equipo es uno de los aspectos más saludables del proyecto.

Hay señales claras de trabajo coordinado:

- entregables documentados;
- trazabilidad por fechas;
- revisiones implícitas vía PR y merges;
- y una estructura de documentación compartida.

La principal limitación es que la colaboración formal convive con un onboarding todavía costoso. En otras palabras, el equipo colabora, pero el sistema aún no facilita del todo esa colaboración a nuevos participantes.

#### Juicio SPACE

**Communication and collaboration: relativamente fuerte para el contexto académico.**

### 6.5 Efficiency and flow

#### Evidencias

- README desactualizado respecto a pruebas y estado del proyecto.
- Setup de base de datos manual.
- Dependencias legacy y repositorio Maven HTTP para PrimeFaces.
- Mezcla de frameworks en controladores.
- Patrones repetidos de field injection y controladores sobrecargados.
- CI existente, pero con validación local aún no simplificada.

#### Lectura

La eficiencia real de desarrollo no depende solo de que haya commits o builds. En este proyecto, la eficiencia está afectada por:

- tiempo de entendimiento;
- tiempo de setup;
- retrabajo por deuda técnica;
- y fricción para ubicar responsabilidades.

El sistema ya tiene algunos aceleradores, como CI y pruebas, pero todavía no logró convertirlos en una experiencia de desarrollo realmente fluida.

#### Juicio SPACE

**Efficiency and flow: mejorable y todavía por debajo de lo deseable.**

---

## 7) Puntos positivos

- El equipo sí dejó trazabilidad del proceso del curso mediante documentos previos y bitácora.
- El proyecto ya cuenta con CI en GitHub Actions y análisis automatizado con SonarQube.
- Se incorporaron pruebas automatizadas y reglas de cobertura con JaCoCo, lo cual mejora los ciclos de validación.
- La colaboración del equipo es visible en historial Git, merges de PR y consolidación documental.
- La deuda técnica ya fue identificada y descrita en entregas anteriores, lo que evita partir de cero.
- La mantenibilidad reportada por SonarQube en la línea base era A, lo que sugiere que todavía existe una ventana razonable para mejorar sin reescribir el proyecto.

---

## 8) Puntos negativos

- El README principal está desactualizado y deteriora onboarding, confianza y autonomía.
- La carga cognitiva es alta por mezcla de JSF, Spring, REST, navegación y SQL nativo en componentes cercanos al borde.
- Persisten hallazgos serios de seguridad y confiabilidad ya documentados en la primera entrega.
- El setup local sigue siendo manual y no reproducible de forma simple.
- La arquitectura favorece controladores y clases base con demasiadas responsabilidades.
- Existen prácticas legacy y de alto acoplamiento: `WebSecurityConfigurerAdapter`, field injection, `@ManagedBean`, `@RequestMapping` repetitivo y reflexión.
- La productividad podría interpretarse erróneamente si se observa solo actividad Git y no se balancea con calidad, fricción y bienestar.

---

## 9) Oportunidades de mejora priorizadas

### 9.1 Corto plazo

1. **Actualizar el README**
   para reflejar el estado real del proyecto, los tests existentes, el flujo de build, la estrategia de base de datos y el uso de CI.

2. **Reducir fricción de setup**
   con un procedimiento reproducible y corto para levantar base de datos y datos iniciales.

3. **Endurecer seguridad básica**
   retirando credenciales hardcodeadas o expuestas, revisando CSRF y eliminando lógica de autenticación insegura.

4. **Definir una medición mínima de DevEx y SPACE**
   usando la mini encuesta propuesta más un conjunto pequeño de indicadores objetivos.

5. **Estabilizar el circuito local de validación**
   con instrucciones claras para ejecutar pruebas y cobertura antes de abrir PR.

### 9.2 Mediano plazo

1. **Separar responsabilidades**
   entre controladores REST, backing beans JSF y lógica de negocio.

2. **Reducir carga cognitiva del núcleo**
   atacando clases base con alta concentración de issues.

3. **Actualizar prácticas legacy**
   migrando configuraciones y patrones deprecados.

4. **Formalizar mejores métricas de colaboración**
   como tiempo de revisión, tiempo de integración y calidad de feedback.

### 9.3 Largo plazo

1. **Rediseñar la arquitectura más acoplada**
   para reducir dependencia de herencia genérica y mezclar menos frameworks.

2. **Normalizar la observabilidad de DevEx**
   con encuestas periódicas y seguimiento histórico de indicadores.

3. **Consolidar un onboarding de bajo esfuerzo**
   con documentación confiable, setup automatizado y validación consistente.

---

## 10) Métricas propuestas y cómo capturarlas

### 10.1 Métricas identificables hoy

| Métrica | Valor o evidencia actual | Fuente | Lectura | Límite |
| :-- | :-- | :-- | :-- | :-- |
| Clases Java principales | 67 | árbol `src/main/java` | Tamaño del sistema a mantener | No mide dificultad real |
| Clases de test | 16 | árbol `src/test/java` | Señal de mejora en feedback técnico | No garantiza buena cobertura efectiva |
| CI activo | Sí | `.github/workflows/build.yml` | Reduce tiempo de validación en PR | No asegura buena experiencia local |
| Umbral JaCoCo líneas | 55% | `pom.xml` | Hay criterio explícito de calidad mínima | Configurar umbral no implica cumplirlo |
| Umbral JaCoCo ramas | 10% | `pom.xml` | Señal de institucionalización inicial | El valor aún es bajo |
| Issues Sonar en línea base | 75 | `docs/PRIMERA_ENTREGA_2026.md` | Magnitud de deuda identificada | Corresponde a una línea base, no a una medición en tiempo real |
| Security hotspots | 7 | `docs/PRIMERA_ENTREGA_2026.md` | Riesgo de seguridad pendiente | Requiere revisión actualizada |
| Security rating | E | `docs/PRIMERA_ENTREGA_2026.md` | Riesgo alto | Es un snapshot histórico |
| Reliability rating | D | `docs/PRIMERA_ENTREGA_2026.md` | Fragilidad del sistema | Es un snapshot histórico |
| Maintainability rating | A | `docs/PRIMERA_ENTREGA_2026.md` | Hay margen de recuperación | No resume calidad total |
| Field injection | 11 usos | escaneo del código | Señal de acoplamiento y menor testabilidad | Es métrica estructural, no de valor |
| `@RequestMapping` | 18 usos | escaneo del código | Muestra estilo legacy extendido | No implica defecto por sí mismo |

### 10.2 Métricas recomendadas a futuro

| Dimensión | Métrica sugerida | Cómo capturarla | Frecuencia |
| :-- | :-- | :-- | :-- |
| DevEx / Feedback loops | Tiempo para obtener resultado de CI | GitHub Actions | Cada PR |
| DevEx / Feedback loops | Tiempo para validar un cambio local | Encuesta + guía de setup | Mensual |
| DevEx / Cognitive load | Percepción de complejidad del código | Mini encuesta | Mensual o por iteración |
| DevEx / Cognitive load | Facilidad para encontrar información | Mini encuesta | Mensual |
| DevEx / Flow state | Frecuencia de interrupciones | Mini encuesta | Mensual |
| SPACE / Satisfaction | eNPS interno o recomendación del equipo | Mini encuesta | Bimestral |
| SPACE / Well-being | Sensación de agotamiento o burnout | Mini encuesta | Bimestral |
| SPACE / Performance | Defectos relevantes o incidentes | Issues / bugs / Sonar / PRs | Por sprint o entrega |
| SPACE / Activity | PRs, commits, revisiones | GitHub | Semanal |
| SPACE / Collaboration | Tiempo de revisión y tiempo de merge | GitHub | Por PR |
| SPACE / Efficiency | Tiempo de onboarding hasta primer cambio exitoso | Medición observacional | Cada nuevo integrante |
| SPACE / Efficiency | Tasa de retrabajo o fixes post-review | Historial Git / PRs | Por iteración |

### 10.3 Tradeoffs y cuidado metodológico

Estas métricas no deben usarse para calificar personas de manera aislada.

- Más actividad no significa automáticamente más productividad.
- Menor tiempo de PR no siempre implica mejor calidad.
- Mayor cobertura no garantiza mejor diseño.
- Mejor satisfacción percibida puede convivir con deuda técnica si el equipo compensa con esfuerzo extra.

La lectura correcta exige siempre combinar señales objetivas, percepción del equipo y contexto del proyecto.

---

## 11) Conclusiones

El análisis del proyecto desde DevEx y SPACE muestra un panorama de **progreso real con fricción estructural persistente**.

Por el lado positivo, el equipo mejoró su base de productividad habilitadora durante el curso:

- incorporó CI;
- configuró análisis con SonarQube;
- añadió pruebas y cobertura mínima;
- y dejó trazabilidad documental clara de la evolución del proyecto.

Por el lado negativo, el sistema todavía impone una experiencia de desarrollo costosa debido a:

- alta carga cognitiva;
- setup local manual;
- documentación principal desactualizada;
- mezcla de responsabilidades y frameworks;
- y hallazgos serios de seguridad y confiabilidad en la línea base de calidad.

La conclusión más importante es que **la productividad del equipo no puede resumirse en commits, merges o líneas de código**. Bajo los marcos DevEx y SPACE, la mejora sostenible depende de reducir fricción, simplificar el entendimiento del sistema, fortalecer la calidad de los ciclos de feedback y medir también percepción, bienestar y colaboración.

En síntesis, el proyecto ya avanzó en prácticas que mejoran productividad, pero todavía necesita atacar varios puntos de fricción para ofrecer una experiencia del desarrollador realmente madura. La prioridad inmediata no debería ser “hacer más”, sino **hacer más fácil, más claro y más seguro desarrollar sobre el sistema**.
