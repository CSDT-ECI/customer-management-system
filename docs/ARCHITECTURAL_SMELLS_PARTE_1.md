# Architectural Smells — CSDT 2026

## Análisis de malas prácticas de arquitectura en `customer-management-system`

> **Propósito del documento**  
> Este archivo identifica y documenta algunos **architectural smells** presentes en el proyecto `customer-management-system`. El objetivo no es listar code smells aislados, sino describir decisiones estructurales y acoplamientos de mayor nivel que afectan la mantenibilidad, la evolución, la claridad de capas y la seguridad del sistema.

---

## 1. Contexto y criterio de análisis

El proyecto parte de una arquitectura monolítica basada en **Spring Boot**, **Spring Data JPA**, **Spring Security** y una capa de presentación construida con **JSF/PrimeFaces**. A simple vista existe una separación por paquetes (`controller`, `service`, `repository`, `model`, `converter`, `configs`), pero la revisión directa del código muestra que esa separación no siempre se traduce en límites arquitectónicos claros.

Para este entregable se distinguen tres fuentes de evidencia:

1. **Evidencia local del proyecto**  
   Revisión directa de clases, anotaciones, dependencias entre capas y rutas principales del repositorio.

2. **Apoyo conceptual de literatura y herramientas**  
   Se usa el paper de la carpeta de materiales para distinguir smells arquitectónicos de smells de diseño o implementación, y se toman categorías reconocidas en Designite y en la taxonomía de Tushar Sharma para nombrar los hallazgos.

3. **Hallazgos históricos ya documentados**  
   Se reutilizan observaciones ya consolidadas en [DEUDA_TECNICA_Y_REFACTORIZACION.md](DEUDA_TECNICA_Y_REFACTORIZACION.md) y [PRIMERA_ENTREGA_2026.md](PRIMERA_ENTREGA_2026.md), especialmente cuando ayudan a reforzar que un problema local también ya fue visible desde SonarCloud.

Este documento **no** afirma smells que no tengan respaldo local suficiente. Por esa razón, aunque existen dependencias mejorables, no se documenta aquí un `Cyclic Dependency` mayor entre capas principales como smell central.

---

## 2. Metodología breve

El análisis siguió este criterio:

- Revisar el código fuente del sistema para localizar responsabilidades arquitectónicas mezcladas, erosión de capas, acoplamientos cruzados y fugas de frontera entre API, dominio, persistencia y vista.
- Contrastar esos hallazgos con la literatura sobre architectural smells, especialmente el paper **A Systematic Mapping Study on Architectural Smells Detection**, que diferencia smells de arquitectura de problemas de diseño e implementación.
- Usar categorías reconocidas por herramientas como **Designite** y por taxonomías académicas para nombrar los smells de forma defendible.
- Apoyar la interpretación con el enfoque de revisión arquitectónica de **TOGAF** y con la idea de revisión por capas y mecanismos críticos propuesta por Microsoft patterns & practices.

---

## 3. Resumen de smells identificados

| # | Architectural smell | Evidencia principal | Impacto dominante |
|:--|:--------------------|:--------------------|:------------------|
| 1 | **God Component / Feature Concentration** | `AbstractController`, `DashboardViewController`, `RandomController` | Concentración de responsabilidades y baja cohesión |
| 2 | **Layer Bypass / Erosion of Layered Architecture** | `DashboardViewController`, `RandomController` | Ruptura de capas y acoplamiento horizontal |
| 3 | **Framework Entanglement / Mixed Component Lifecycle** | `DashboardViewController`, `LoginController`, `LoginComponent` | Ambigüedad de ciclo de vida y gobernanza técnica débil |
| 4 | **Leaky Architectural Boundaries** | `MainRestController`, `AbstractController`, `SecurityUser` | Fronteras débiles entre API, dominio y persistencia |
| 5 | **Data/Query Concern Embedded in Domain and Presentation** | `Dashboard`, `DashboardViewController` | Mezcla de datos, consulta, visualización y acceso a persistencia |

---

## 4. Smells arquitectónicos documentados

### 4.1 God Component / Feature Concentration

**Definición y clasificación**  
Designite define `God Component` como un componente excesivamente grande en tamaño o responsabilidades, y `Feature Concentration` como un componente que realiza más de una preocupación arquitectónica. Ambos nombres son adecuados cuando una misma pieza concentra lógica transversal y actúa como punto de acoplamiento del resto del sistema.

**Evidencia local**

- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java) mezcla:
  - operaciones REST (`GET`, `POST`, `PUT`, `DELETE`);
  - comportamiento reutilizado por controladores concretos;
  - estado de UI (`list`, `selectedObject`);
  - navegación JSF (`save()`, `edit()`, `cancel()`);
  - reflexión para construir instancias (`newInstance()`).
- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java) reúne lógica de inicialización, composición de gráficos, ejecución de consultas, acceso a persistencia y adaptación a PrimeFaces.
- [src/main/java/com/cms/controller/RandomController.java](../src/main/java/com/cms/controller/RandomController.java) orquesta generación de datos, navegación entre otros controladores, composición de objetos y persistencia.

**Por qué es un problema arquitectónico**  
No se trata solo de métodos largos o malas decisiones de estilo. El problema es que ciertos componentes se convierten en centros de gravedad del sistema: concentran reglas, infraestructura, estado y colaboración con demasiados elementos. Eso degrada la arquitectura porque vuelve difícil entender qué capa es responsable de qué.

**Impacto**

- Reduce la cohesión de componentes clave.
- Aumenta el costo de cambio porque una modificación en una responsabilidad puede afectar varias más.
- Favorece que nuevas funcionalidades sigan entrando en las mismas clases base, profundizando la deuda.
- Dificulta pruebas más finas y refactors seguros.

**Remediación recomendada**

- Separar responsabilidades de navegación JSF y exposición REST.
- Reducir el rol de `AbstractController` a una sola responsabilidad o eliminar la base genérica en favor de composición.
- Extraer de `DashboardViewController` la lógica de construcción de datos y de acceso a consultas.
- Mover de `RandomController` la generación y persistencia a servicios especializados.

---

### 4.2 Layer Bypass / Erosion of Layered Architecture

**Definición y clasificación**  
Este smell aparece cuando las capas conceptuales existen en la estructura del proyecto, pero en la práctica son atravesadas o ignoradas. No es una categoría única con un solo nombre universal, pero describe una forma clara de **erosión arquitectónica**: los componentes dejan de comunicarse por los límites previstos y empiezan a saltarse la capa intermedia.

**Evidencia local**

- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java) usa `EntityManager` directamente y ejecuta `createNativeQuery(query)` desde un controlador.
- Ese mismo controlador depende de [DashboardController.java](../src/main/java/com/cms/controller/DashboardController.java) en lugar de depender de un servicio orientado al caso de uso.
- [src/main/java/com/cms/controller/RandomController.java](../src/main/java/com/cms/controller/RandomController.java) depende directamente de múltiples controladores:
  - `UnitController`
  - `FirstNameController`
  - `LastNameController`
  - `UnitIndustryController`
  - `UnitTypeController`
  - `CountryController`

**Por qué es un problema arquitectónico**  
La arquitectura sugiere una separación `controller -> service -> repository/model`, pero varios flujos relevantes no la respetan. Cuando un controlador accede a persistencia o consume otros controladores, la capa de aplicación pierde su frontera y deja de ser una organización por responsabilidades para convertirse solo en una convención de carpetas.

**Impacto**

- Incrementa el acoplamiento horizontal entre controladores.
- Debilita la capa de servicio como lugar natural de coordinación y reglas de negocio.
- Hace más difícil reemplazar mecanismos de persistencia o reutilizar casos de uso.
- Aumenta el riesgo de efectos laterales al modificar un flujo.

**Remediación recomendada**

- Prohibir acceso a `EntityManager` desde controladores.
- Reubicar la coordinación entre controladores en servicios o facades de aplicación.
- Hacer que los controladores dependan de casos de uso, no de otros controladores.
- Definir explícitamente la regla arquitectónica `controller -> service -> repository`.

---

### 4.3 Framework Entanglement / Mixed Component Lifecycle

**Definición y clasificación**  
La literatura sobre architectural smells y deuda arquitectónica insiste en que los problemas estructurales también aparecen cuando conviven mecanismos incompatibles o ambiguos para manejar responsabilidades equivalentes. Aquí el smell no es “usar dos frameworks” por sí mismo, sino **entrelazar sus ciclos de vida, roles y anotaciones sobre los mismos componentes**.

**Evidencia local**

- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java) combina `@RestController`, `@ManagedBean` y `@RequestScoped`.
- [src/main/java/com/cms/controller/LoginController.java](../src/main/java/com/cms/controller/LoginController.java) mezcla `@Component` con `@SessionScoped`.
- [src/main/java/com/cms/contextHolder/LoginComponent.java](../src/main/java/com/cms/contextHolder/LoginComponent.java) y [HelloWorld.java](../src/main/java/com/cms/contextHolder/HelloWorld.java) siguen usando `@ManagedBean`.
- El proyecto mezcla artefactos de Spring MVC/REST con beans JSF y navegación XHTML dentro del mismo borde de presentación.

**Por qué es un problema arquitectónico**  
Este smell afecta la gobernanza de la presentación y la forma en que el sistema administra estado, inyección y responsabilidades. La pregunta arquitectónica deja de ser “qué hace cada componente” y pasa a ser “quién lo gobierna realmente: JSF o Spring”. Esa ambigüedad es estructural porque impacta el modelo mental de todo el sistema.

**Impacto**

- Vuelve opaco el ciclo de vida real de los componentes.
- Dificulta migraciones, modernización y troubleshooting.
- Debilita la consistencia del borde de presentación.
- Favorece errores por supuestos distintos sobre scope, inicialización y estado.

**Remediación recomendada**

- Definir un solo mecanismo rector para el borde de presentación.
- Encapsular o retirar progresivamente los managed beans de JSF si el rumbo es Spring.
- Evitar anotaciones mezcladas de distintos frameworks en una misma clase.
- Documentar explícitamente la arquitectura objetivo de presentación para detener la erosión.

---

### 4.4 Leaky Architectural Boundaries

**Definición y clasificación**  
Este smell aparece cuando las fronteras entre capas o contextos internos y externos son demasiado débiles. En términos prácticos, la API expone estructuras internas de persistencia o deja que detalles del dominio y del almacenamiento crucen el borde sin mediación.

**Evidencia local**

- [src/main/java/com/cms/controller/MainRestController.java](../src/main/java/com/cms/controller/MainRestController.java) recibe y retorna `SecurityUser` directamente en `/api/user/register`.
- [src/main/java/com/cms/model/security/SecurityUser.java](../src/main/java/com/cms/model/security/SecurityUser.java) es una entidad JPA con semántica de persistencia y relación con roles.
- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java) expone entidades del modelo directamente en endpoints genéricos REST.
- [src/main/java/com/cms/service/AbstractService.java](../src/main/java/com/cms/service/AbstractService.java) refuerza ese acoplamiento al operar CRUD genérico sobre entidades sin capa de contrato separada.

**Por qué es un problema arquitectónico**  
Aquí el problema no es un DTO faltante aislado. La arquitectura del borde REST está acoplada a la persistencia. Eso significa que cambios internos del modelo afectan contratos externos y viceversa. El límite API-dominio queda erosionado y la frontera deja de proteger al sistema.

**Impacto**

- Expone detalles internos del modelo y de persistencia.
- Dificulta versionado y evolución de la API.
- Aumenta el riesgo de seguridad y de serialización no deseada.
- Refuerza el acoplamiento entre controladores, entidades y repositorios.

**Remediación recomendada**

- Introducir DTOs de entrada y salida para los endpoints REST.
- Restringir el uso de entidades JPA al interior de servicios y repositorios.
- Evitar controladores CRUD genéricos que publiquen el modelo interno como API.
- Definir contratos explícitos por caso de uso.

---

### 4.5 Data/Query Concern Embedded in Domain and Presentation

**Definición y clasificación**  
Este smell describe una mezcla estructural entre preocupación de datos, configuración de consulta, lógica de recuperación y representación. Aunque no siempre aparece con un único nombre estándar, se alinea con problemas de **mezcla de concerns** y violación de límites entre dominio, persistencia y presentación.

**Evidencia local**

- [src/main/java/com/cms/model/Dashboard.java](../src/main/java/com/cms/model/Dashboard.java) almacena una consulta SQL en el atributo `query`.
- Esa misma entidad también guarda configuración de visualización como `legendPosition`, `showColumn`, `seriesTags`, `ymin` y `ymax`.
- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java) ejecuta directamente `entityManager.createNativeQuery(query)` usando ese valor almacenado en la entidad.
- Después, el controlador transforma el resultado en modelos de gráfico de PrimeFaces en el mismo flujo.

**Por qué es un problema arquitectónico**  
La arquitectura del dashboard mezcla en el mismo circuito:

- definición del dato;
- consulta a persistencia;
- configuración de visualización;
- construcción de widgets de presentación.

Eso rompe la separación entre modelo, acceso a datos y vista. No es solo una mala consulta: es una estructura donde las preocupaciones quedaron ensambladas de forma rígida.

**Impacto**

- Hace frágil la evolución del módulo de dashboards.
- Acopla la representación visual a la forma exacta de consultar la base.
- Aumenta riesgo de seguridad y de validación insuficiente sobre consultas.
- Impide reutilizar la lógica de datos sin arrastrar la capa de presentación.

**Remediación recomendada**

- Separar la definición del dashboard de la ejecución de consultas.
- Centralizar el acceso a datos del dashboard en un servicio o repositorio especializado.
- Restringir o validar fuertemente la ejecución de SQL configurable.
- Mantener en el controlador solo la adaptación final hacia modelos de vista.

---

## 5. Priorización de remediación

### Prioridad 1 — Riesgo estructural y de frontera

1. **Layer Bypass / Erosion of Layered Architecture**  
   Debe abrir el plan de mejora porque rompe la organización por capas y debilita el punto natural de coordinación del sistema.

2. **Leaky Architectural Boundaries**  
   Tiene efecto directo sobre seguridad, evolución de API y exposición indebida del modelo interno.

3. **Data/Query Concern Embedded in Domain and Presentation**  
   Afecta un flujo funcional sensible y mezcla de manera riesgosa consulta, entidad y visualización.

### Prioridad 2 — Mantenibilidad estructural

1. **God Component / Feature Concentration**  
   Es clave porque concentra deuda en componentes base y multiplica el costo de cada cambio.

### Prioridad 3 — Gobernanza y claridad arquitectónica

1. **Framework Entanglement / Mixed Component Lifecycle**  
   Aunque no siempre rompe el sistema de inmediato, erosiona su comprensión, complica modernización y normaliza decisiones inconsistentes.

---

## 6. Conclusión

El proyecto no carece de estructura; de hecho, conserva una organización por paquetes y una intención de separar capas. Sin embargo, la revisión arquitectónica muestra que esa estructura está **erosionada** por varios smells fuertes: concentración excesiva de responsabilidades, saltos entre capas, mezcla de frameworks en la presentación, fronteras débiles entre API y persistencia, y un módulo de dashboards donde consulta, dato y vista quedaron acoplados.

La implicación principal es que la deuda del sistema no debe leerse solo como acumulación de code smells. Una parte importante del problema ya es **deuda arquitectónica**, porque afecta límites, responsabilidades, capacidad de evolución y claridad del diseño. En ese sentido, el sistema sigue siendo recuperable, pero requiere priorizar remediaciones que restablezcan fronteras y responsabilidades antes de cualquier limpieza cosmética.

---

## 7. Referencias

### Referencias usadas como marco conceptual

- Mumtaz, H., Singh, P., Blincoe, K. **A Systematic Mapping Study on Architectural Smells Detection**. Material local en [Materiales Architectural Debt/2020_JSS_ArchSmellsSMS.pdf](</C:/Users/jesjc/OneDrive/Documentos/CSDT/Materiales Architectural Debt/2020_JSS_ArchSmellsSMS.pdf>).
- Designite. [Features](https://www.designite-tools.com/docs/features.html).
- Tushar Sharma. [A Taxonomy of Software Smells](https://www.tusharma.in/smells/).
- The Open Group. [Architecture Review Checklist - System Engineering / Overall Architecture](https://www.opengroup.org/architecture/togaf7-doc/arch/p4/comp/clists/syseng.htm).
- Microsoft patterns & practices. Referencia indicada en el enunciado: [Architecture and Design Review Checklists](https://docs.microsoft.com/en-us/previous-versions/msp-n-p/ff647464(v=pandp.10)?redirectedfrom=MSDN).

### Referencias internas del proyecto

- [DEUDA_TECNICA_Y_REFACTORIZACION.md](DEUDA_TECNICA_Y_REFACTORIZACION.md)
- [PRIMERA_ENTREGA_2026.md](PRIMERA_ENTREGA_2026.md)
- [RefactDone_CSDT-2026.md](../RefactDone_CSDT-2026.md)
