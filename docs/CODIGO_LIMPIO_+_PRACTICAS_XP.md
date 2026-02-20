# Autores
- Juan David Rodriguez Rodriguez
- Jesus Alberto Jauregui Conde
- David Santiago Castro Sierra

---

## Contenido
1. Contexto del sistema
2. Características de Clean Code (evaluación + evidencias)
3. Principios de programación incumplidos
4. Prácticas XP recomendadas (aplicadas al repo)
5. Plan de mejora incremental (iteraciones)
6. Checklist de Pull Request
7. Apéndice: evidencias y enlaces

---

## 1) Contexto del sistema

### 1.1 Stack y estilo arquitectónico
- Aplicación **Spring Boot** con interfaz **JSF/PrimeFaces** (JoinFaces).
- Estructura nominal por paquetes `controller/`, `service/`, `repository/`, `model/`.
- En varios puntos se observa una mezcla fuerte entre:
	- Backing beans JSF (`@ManagedBean`)
	- Controladores REST (`@RestController`)
	- Acceso a datos (consultas nativas) dentro de controladores

**Evidencias representativas**
- Controlador base genérico (endpoints + navegación + utilidades): [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)
- Mezcla REST + JSF + SQL nativo + modelos de UI: [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)
- Config de seguridad (enfoque legacy): [src/main/java/com/cms/configs/SecurityConfig.java](../src/main/java/com/cms/configs/SecurityConfig.java)

### 1.2 Estado actual del proyecto (impacta Clean Code y XP)
- No hay tests automatizados declarados: [README.md](../README.md)
- Hay un documento de deuda técnica / code smells ya elaborado (sirve como base para el backlog): [DEUDA_TECNICA_Y_REFACTORIZACION.md](DEUDA_TECNICA_Y_REFACTORIZACION.md)
- Configuración de runtime relevante:
	- [src/main/resources/application.properties](../src/main/resources/application.properties)

---

## 2) Características de Clean Code (evaluación + evidencias)

> Criterio: Clean Code no es solo estilo; es **legibilidad**, **cambios seguros**, **bajo acoplamiento**, **responsabilidades claras** y **correctness** (seguridad/errores).

En cada característica se listan: **Estado**, **Evidencias** y **Recomendaciones accionables**.

### 2.1 Nombres significativos y consistentes
**Estado:** Parcial

**Evidencias**
- Parámetros/atributos genéricos o poco expresivos como `tobject` y colecciones con intención difusa.
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)
- Inconsistencias y typos en constantes y naming (por ejemplo, `BAR_CHAR_MODEL`).
	- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)
- Nombres con errores ortográficos que dificultan lectura y búsqueda.
	- [src/main/java/com/cms/util/RandomUtility.java](../src/main/java/com/cms/util/RandomUtility.java)

**Recomendaciones accionables**
- Definir convención: idioma, casing, y términos del dominio (Customer/Person/Unit).
- Renombrar a intención: `tobject` → `entity`, `graphicList` → `dashboardCharts`.
- Corregir typos para mejorar mantenibilidad (esto es un refactor seguro si hay tests mínimos).

### 2.2 Funciones pequeñas y con una sola responsabilidad
**Estado:** No cumple

**Evidencias**
- Métodos grandes que mezclan reglas, acceso a datos y armado de UI.
	- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)

**Recomendaciones accionables**
- Extraer colaboraciones:
	- Servicio para obtener dashboards (políticas de visibilidad)
	- Servicio para ejecutar queries de dashboard con restricciones
	- Factoría/estrategia de modelos de gráficos
- Aplicar el patrón Strategy: `ChartType -> ChartBuilder` para eliminar `if/else if`.

### 2.3 Clases cohesivas (una razón para cambiar)
**Estado:** No cumple

**Evidencias**
- Clase base de controlador con responsabilidades múltiples: endpoints REST, navegación JSF, creación por reflexión, utilidades varias.
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)

**Recomendaciones accionables**
- Evitar herencia como “reuso de controladores”; preferir composición.
- Separar “backing bean JSF genérico” de “REST controller genérico”.
- Remover utilidades no relacionadas del controlador base.

### 2.4 Duplicación (DRY) y ruido accidental
**Estado:** No cumple

**Evidencias**
- Ramificaciones redundantes en inicialización (un `if/else` que hace lo mismo) creando ruido y sospecha de bugs.
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)

**Recomendaciones accionables**
- Simplificar el flujo a una sola ruta.
- Regla de equipo: si un condicional no cambia comportamiento, removerlo o documentar por qué existe.

### 2.5 Comentarios: intención y reglas, no historia
**Estado:** Parcial

**Evidencias**
- Comentarios tipo “Created by …” no explican intención ni reglas.
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)

**Recomendaciones accionables**
- Mover “historia” al control de versiones (commit messages / PR).
- Mantener comentarios solo para:
	- reglas de negocio
	- decisiones (trade-offs)
	- restricciones (por qué no se pudo hacer de otra forma)

### 2.6 Manejo de errores y logging
**Estado:** Parcial

**Evidencias**
- Logging por concatenación y sin stacktrace (pierde diagnóstico).
	- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)

**Recomendaciones accionables**
- Usar `logger.error("mensaje", ex)`.
- Definir política de errores:
	- qué se muestra al usuario
	- qué se registra
	- cómo se correlaciona un error (requestId/traceId)

### 2.7 Diseño simple (KISS) vs magia
**Estado:** No cumple

**Evidencias**
- Instanciación por reflexión (`newInstance`) que oculta dependencias, es frágil y rompe testabilidad.
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)

**Recomendaciones accionables**
- Reemplazar por fábrica explícita o `Supplier<T>`.
- En componentes gestionados por Spring, preferir inyección por constructor.

### 2.8 Estructuras de datos y límites (boundaries)
**Estado:** No cumple (alto riesgo)

**Evidencias**
- SQL nativo construido desde strings, ejecutado desde controlador.
	- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)
- Modelo que persiste un query como texto (`@Lob`) y tags como CSV en una sola columna.
	- [src/main/java/com/cms/model/Dashboard.java](../src/main/java/com/cms/model/Dashboard.java)

**Recomendaciones accionables**
- Evitar ejecutar SQL arbitrario almacenado. Alternativas:
	- catálogo/whitelist de queries
	- vistas materializadas
	- parámetros seguros
- Para `seriesTags`, usar tabla normalizada o encapsularlo mejor como Value Object.

### 2.9 Seguridad como parte de la corrección del código
**Estado:** No cumple (crítico)

**Evidencias**
- CSRF deshabilitado en configuración de seguridad.
	- [src/main/java/com/cms/configs/SecurityConfig.java](../src/main/java/com/cms/configs/SecurityConfig.java)
- Login hardcodeado (usuario/clave) en lógica.
	- [src/main/java/com/cms/contextHolder/LoginComponent.java](../src/main/java/com/cms/contextHolder/LoginComponent.java)
- CORS abierto con `@CrossOrigin("*")` en controladores.
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)
	- [src/main/java/com/cms/controller/RandomController.java](../src/main/java/com/cms/controller/RandomController.java)
	- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)
- Credenciales de base de datos en texto plano en configuración.
	- [src/main/resources/application.properties](../src/main/resources/application.properties)

**Recomendaciones accionables**
- Remover credenciales hardcodeadas y usar autenticación real (al menos por usuarios persistidos).
- Re-evaluar CSRF para formularios JSF.
- Restringir CORS por origen/endpoint.
- Mover secretos a variables de entorno o vault.

### 2.10 Pruebas automatizadas (feedback rápido)
**Estado:** No cumple

**Evidencias**
- El repositorio declara no tener tests.
	- [README.md](../README.md)

**Recomendaciones accionables**
- Empezar por tests “baratos” (utilidades, factories, validaciones) y crecer hacia integración.
- Incorporar CI para que cada PR ejecute tests.

### 2.11 Performance y complejidad accidental
**Estado:** Parcial

**Evidencias**
- Concatenación de strings en bucles y naming inconsistente.
	- [src/main/java/com/cms/util/RandomUtility.java](../src/main/java/com/cms/util/RandomUtility.java)

**Recomendaciones accionables**
- Usar `StringBuilder` en loops.
- Simplificar métodos y renombrar para eliminar confusión.

---

## 3) Principios de programación incumplidos (conclusiones)

### 3.1 SOLID

**SRP (Single Responsibility Principle)**
- Conclusión: clases cambian por demasiadas razones.
- Evidencias:
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)
	- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)

**OCP (Open/Closed Principle)**
- Conclusión: agregar un nuevo tipo de gráfico implica modificar condicionales.
- Evidencia:
	- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)

**DIP (Dependency Inversion Principle)**
- Conclusión: controladores dependen de otros controladores (capa web acoplada consigo misma).
- Evidencia:
	- [src/main/java/com/cms/controller/RandomController.java](../src/main/java/com/cms/controller/RandomController.java)

### 3.2 DRY
- Conclusión: duplicación/ruido reduce claridad y facilita inconsistencias.
- Evidencia:
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)

### 3.3 KISS
- Conclusión: reflexión y magia aportan complejidad accidental.
- Evidencia:
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)

### 3.4 Ley de Demeter (LoD)
- Conclusión: clases con demasiado conocimiento de otras partes del sistema.
- Evidencia:
	- [src/main/java/com/cms/controller/RandomController.java](../src/main/java/com/cms/controller/RandomController.java)

### 3.5 Separation of Concerns (SoC)
- Conclusión: UI, API, negocio y data access se mezclan.
- Evidencia:
	- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)

---

## 4) Prácticas XP recomendadas

### 4.1 TDD (Test-Driven Development)
**Por qué aquí:** el repo necesita una red de seguridad para refactor.

**Aplicación práctica inmediata**
- Comenzar con clases sin framework:
	- [src/main/java/com/cms/util/RandomUtility.java](../src/main/java/com/cms/util/RandomUtility.java)
- Luego, tests de servicios (mock repositorios) cuando haya lógica separada del controlador.

### 4.2 Integración continua (CI)
**Objetivo:** feedback automático por PR.

**Aplicación práctica**
- Crear workflow para ejecutar build y tests Maven.
- Fijar una versión de Java (Java 8) o adaptar el build para JDK moderno.

### 4.3 Refactoring continuo
**Regla XP:** refactor pequeño, frecuente y con feedback.

**Aplicación práctica**
- 1 smell = 1 PR.
- Priorizar smells que reducen acoplamiento:
	- Separar [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)
	- Quitar reflexión de [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)

### 4.4 Pair programming
- Rotación de parejas.
- Un conductor escribe, el navegador revisa intención y diseño.

### 4.5 Collective Code Ownership
- Cualquier persona del equipo puede tocar cualquier archivo.
- Se refuerza con estándares + CI + PRs.

### 4.6 Small Releases
- Cambios pequeños, demostrables.
- Ejemplo de releases por iteración: build estable → tests mínimos → refactor 1 controlador → hardening seguridad.

### 4.7 Coding standards
- Acordar convenciones:
	- naming
	- logging
	- manejo de excepciones
	- separación JSF vs REST

---

## 6) Apéndice: evidencias y enlaces

- Documento previo de smells/deuda técnica (detalle): [DEUDA_TECNICA_Y_REFACTORIZACION.md](DEUDA_TECNICA_Y_REFACTORIZACION.md)
- Archivos clave analizados:
	- [src/main/java/com/cms/controller/AbstractController.java](../src/main/java/com/cms/controller/AbstractController.java)
	- [src/main/java/com/cms/controller/DashboardViewController.java](../src/main/java/com/cms/controller/DashboardViewController.java)
	- [src/main/java/com/cms/controller/RandomController.java](../src/main/java/com/cms/controller/RandomController.java)
	- [src/main/java/com/cms/configs/SecurityConfig.java](../src/main/java/com/cms/configs/SecurityConfig.java)
	- [src/main/java/com/cms/contextHolder/LoginComponent.java](../src/main/java/com/cms/contextHolder/LoginComponent.java)
	- [src/main/java/com/cms/service/AbstractService.java](../src/main/java/com/cms/service/AbstractService.java)
	- [src/main/java/com/cms/util/RandomUtility.java](../src/main/java/com/cms/util/RandomUtility.java)
	- [src/main/java/com/cms/model/Dashboard.java](../src/main/java/com/cms/model/Dashboard.java)
	- [src/main/resources/application.properties](../src/main/resources/application.properties)
	- [pom.xml](../pom.xml)
