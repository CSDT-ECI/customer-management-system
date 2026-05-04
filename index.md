<div align="center">

# 🛠️ RefactDone — CSDT 2026
### *Calidad de Software y Gestión de Deuda Técnica*

[![Curso](https://img.shields.io/badge/Curso-CSDT_2026-6C63FF?style=for-the-badge)](./)
[![Estado](https://img.shields.io/badge/Estado-Completado-22c55e?style=for-the-badge)](./)
[![Entregas](https://img.shields.io/badge/Entregas-5_de_5-f59e0b?style=for-the-badge)](./)
[![Tests](https://img.shields.io/badge/Tests-100%2B_unitarios-3b82f6?style=for-the-badge)](./)

> **Un recorrido completo por el análisis, refactorización y mejora de calidad  
> del proyecto `customer-management-system` — Spring Boot · JSF · Maven · SonarQube**

</div>

---

## 👥 Equipo RefactDone

| Integrante | GitHub |
|---|---|
| Juan David Rodriguez Rodriguez | [@Enigmus12](https://github.com/Enigmus12) |
| Jesus Alberto Jauregui Conde | [@JesusJC15](https://github.com/JesusJC15) |
| David Santiago Castro Sierra | [@daviidc29](https://github.com/daviidc29) |

---

## 📊 Dashboard de Métricas del Proyecto

> Métricas consolidadas del análisis SonarQube Cloud sobre la línea base del proyecto.

| Dimensión | Valor | Tendencia |
|---|---|---|
| 🔴 Security Rating | **E → revisado** | Vulnerabilidades identificadas y priorizadas |
| 🟠 Reliability Rating | **D** | 14 issues (2 high, 12 medium) |
| 🟢 Maintainability | **A** | 69 code smells — deuda manejable |
| 🧪 Cobertura inicial | **0.0%** | Sin tests automatizados al inicio |
| 🧪 Cobertura final | **~45%** | 100+ tests unitarios incorporados |
| 📋 Issues totales | **76 abiertos** | 621 min de deuda estimada |
| 🔁 Duplicación | **0.0%** | Sin copia masiva de bloques |
| 🔥 Security Hotspots | **7** | 100% requirieron revisión manual |

### Distribución de Issues por Severidad

```
BLOCKER  ██░░░░░░░░░░░░░░░░░░   2   (2.7%)
CRITICAL ████░░░░░░░░░░░░░░░░  10  (13.3%)
MAJOR    ████████████░░░░░░░░  30  (40.0%)
MINOR    █████████████░░░░░░░  34  (44.0%)
```

### Distribución por Tipo de Hallazgo

```
Code Smells   ████████████████████████░  69  (92.0%)
Bugs          █░░░░░░░░░░░░░░░░░░░░░░░░   3   (4.0%)
Vulnerability █░░░░░░░░░░░░░░░░░░░░░░░░   4   (4.0%)
```

### Concentración de Deuda por Archivo

| Archivo | Issues | Participación | Esfuerzo |
|---|---|---|---|
| `AbstractController.java` | 21 | **28.0%** | 188 min |
| `AbstractService.java` | 11 | 14.7% | 116 min |
| `RandomUtility.java` | 7 | 9.3% | 36 min |
| `RandomController.java` | 7 | 9.3% | 32 min |
| `DashboardViewController.java` | 6 | 8.0% | 27 min |
| `SecurityConfig.java` | 4 | 5.3% | 85 min |

> Los 5 archivos más afectados concentran **52 de 76 issues (69.3%)** — deuda localizada en componentes base.

---

## 🗓️ Bitácora del Curso

```
Feb 12 ──────────────────────────────────────────── Mar 29
  │           │            │           │          │
  ▼           ▼            ▼           ▼          ▼
[E1]        [E2]         [E3]        [E4]       [E5]
Refact.   Clean Code   Primera    DevEx +    Deuda en
+ Smells   + XP        Entrega   Productiv. Procesos
  ✅          ✅           ✅          ✅         ✅
```

| Fecha | Entregable | Documento | Estado |
|---|---|---|---|
| 2026-02-12 | Refactoring + Code Smells | [DEUDA_TECNICA_Y_REFACTORIZACION.md](docs/DEUDA_TECNICA_Y_REFACTORIZACION.md) | ✅ Completado |
| 2026-02-19 | Clean Code + XP Practices | [CODIGO_LIMPIO_+_PRACTICAS_XP.md](docs/CODIGO_LIMPIO_+_PRACTICAS_XP.md) | ✅ Completado |
| 2026-03-15 | Primera Entrega SonarQube | [PRIMERA_ENTREGA_2026.md](docs/PRIMERA_ENTREGA_2026.md) | ✅ Completado |
| 2026-03-22 | DevEx + Developer Productivity | [DEVEX_Y_DEVELOPER_PRODUCTIVITY.md](docs/DEVEX_Y_DEVELOPER_PRODUCTIVITY.md) | ✅ Completado |
| 2026-03-29 | Deuda Técnica en Procesos (CI) | [DEUDA_TECNICA_EN_PROCESOS.md](docs/DEUDA_TECNICA_EN_PROCESOS.md) | ✅ Completado |

---

## 📰 Entregable 1 — Refactoring + Code Smells

> **Fecha:** 12 Feb 2026 · [Ver documento completo →](docs/DEUDA_TECNICA_Y_REFACTORIZACION.md)

Se identificaron **35+ code smells** distribuidos en 6 categorías de severidad. El nivel de deuda técnica fue clasificado como **alto**, con áreas críticas en seguridad, arquitectura y mantenibilidad.

### 🔴 Hallazgos Críticos de Seguridad (6 issues)

| # | Smell | Archivo | OWASP |
|---|---|---|---|
| 1 | Credenciales hardcodeadas `if(login.equals("admin"))` | `LoginComponent` | A07:2021 |
| 2 | Contraseñas en texto plano en properties | `application.properties` | A02:2021 |
| 3 | CSRF deshabilitado `http.csrf().disable()` | `SecurityConfig` | A01:2021 |
| 4 | SQL dinámico sin sanitizar (`@Lob` query) | `Dashboard` | A03:2021 |
| 5 | `WebSecurityConfigurerAdapter` deprecated | `SecurityConfig` | — |
| 6 | `main()` con lógica BCrypt en clase de config | `SecurityConfig` | — |

### 🟠 Problemas de Arquitectura (8 issues)

- **God Class** — `AbstractController` con 5+ responsabilidades mezcladas
- **Mezcla JSF + Spring** — `@ManagedBean` y `@Component` conviviendo
- **Field Injection** — `@Autowired` en campos, dificulta testing
- **Clases vacías** — `PersonController`, `CountryController`, `PersonService` sin lógica propia
- **Violación SRP** — misma clase actúa de REST Controller y JSF Managed Bean

### 🟡 Propuestas de Refactorización Aplicadas

| Técnica | Aplicada en |
|---|---|
| Extract Method | `RandomController.createRandomUnit()` |
| Replace Inheritance with Delegation | Jerarquía `AbstractController` |
| Builder Pattern (`@Builder` Lombok) | Entidades con múltiples campos |
| Introduce DTO | Capa REST — desacoplar API de entidades JPA |
| Constructor Injection | Todos los componentes Spring |
| Replace Query String → Criteria API | `AbstractService.findByName()` |
| Extract Class | `AbstractController` — separar responsabilidades |
| Replace Magic String with Constant | `AbstractController`, `SecurityConfig` |

---

## 📰 Entregable 2 — Clean Code + XP Practices

> **Fecha:** 19 Feb 2026 · [Ver documento completo →](docs/CODIGO_LIMPIO_+_PRACTICAS_XP.md)

Evaluación de 11 características de Clean Code con evidencia directa del repositorio, y mapeo a prácticas de Extreme Programming.

### Evaluación Clean Code

| Característica | Estado | Observación clave |
|---|---|---|
| Nombres significativos | ⚠️ Parcial | `tobject`, `BAR_CHAR_MODEL` — nombres genéricos e inconsistentes |
| Funciones pequeñas (SRP) | ❌ No cumple | Métodos grandes que mezclan reglas, datos y UI |
| Clases cohesivas | ❌ No cumple | `AbstractController` cambia por múltiples razones |
| DRY / Sin duplicación | ❌ No cumple | `if/else` que ejecuta lo mismo en ambas ramas |
| Comentarios de intención | ⚠️ Parcial | Solo hay autoría, no reglas de negocio |
| Manejo de errores | ⚠️ Parcial | Logging por concatenación, sin stacktrace |
| KISS (sin magia) | ❌ No cumple | `newInstance()` por reflexión — frágil y no testeable |
| Límites y estructuras | ❌ No cumple | SQL nativo en controlador; CSV en columna (`seriesTags`) |
| Seguridad | ❌ Crítico | CSRF off, credenciales hard, CORS `"*"` |
| Pruebas automatizadas | ❌ No cumple | 0 tests al inicio del curso |
| Performance | ⚠️ Parcial | Concatenación en loops, eager fetching |

### Principios SOLID Violados

```
SRP  ── AbstractController y DashboardViewController: múltiples razones de cambio
OCP  ── Agregar un gráfico nuevo requiere modificar if/else existentes
DIP  ── Controllers dependen de otros controllers (capa web acoplada a sí misma)
```

### Prácticas XP Recomendadas

- **TDD** — Empezar con `RandomUtility`, luego servicios con mocks
- **CI** — Workflow Maven + SonarQube en cada push y PR
- **Refactoring continuo** — 1 smell = 1 PR, priorizar reducción de acoplamiento
- **Pair programming** — Rotación de roles conductor/navegador
- **Collective Code Ownership** — Cualquier integrante puede tocar cualquier archivo
- **Small Releases** — Build estable → tests mínimos → refactor → hardening

---

## 📰 Entregable 3 — Primera Entrega: Modelos de Calidad (SonarQube)

> **Fecha:** 15 Mar 2026 · [Ver documento completo →](docs/PRIMERA_ENTREGA_2026.md)

Análisis integral basado en **ISO/IEC 25010** con evidencia de SonarQube Cloud.

### Estado General del Tablero SonarQube

```
Seguridad      [E] ████████████ CRÍTICO
Confiabilidad  [D] ████████░░░░ DÉBIL
Mantenibilidad [A] ████░░░░░░░░ ACEPTABLE (ventana de oportunidad)
Cobertura      [—] ░░░░░░░░░░░░ 0.0% (línea base)
Duplicación    [—] ████████████ 0.0% (positivo)
Hotspots       [7] ████████░░░░ 100% pendientes de revisión
```

### Hallazgos Bloqueantes y Críticos

| Severidad | Tipo | Archivo | Hallazgo |
|---|---|---|---|
| 🔴 BLOCKER | VULNERABILITY | `SecurityConfig.java:39` | Contraseña comprometida — revocar y cambiar |
| 🔴 BLOCKER | CODE_SMELL | `UnitService.java:20` | `entityManager` sombrea campo heredado de `AbstractService` |
| 🟠 CRITICAL | VULNERABILITY | `MainRestController.java:20` | Entidad persistente expuesta como contrato REST |
| 🟠 CRITICAL | BUG | `AbstractController.java:158` | `new Random()` recreado en cada llamada |
| 🟠 CRITICAL | CODE_SMELL | `AbstractController.java:27` | Campo `list` no transient en clase serializable |

### Distribución de Deuda por Paquete

```
controller     ████████████████████████░░  39 issues  52.0%  265 min
service        █████████░░░░░░░░░░░░░░░░░  14 issues  18.7%  156 min
util           ████░░░░░░░░░░░░░░░░░░░░░░   7 issues   9.3%   36 min
model          ██░░░░░░░░░░░░░░░░░░░░░░░░   5 issues   6.7%   10 min
configs        ██░░░░░░░░░░░░░░░░░░░░░░░░   4 issues   5.3%   85 min
contextHolder  █░░░░░░░░░░░░░░░░░░░░░░░░░   3 issues   4.0%   22 min
```

### Incorporación de Tests

- Se partió de **0.0% de cobertura** sin carpeta `src/test`
- Se incorporaron **100+ tests unitarios** sobre modelos, utilidades y controllers
- Se usaron **MockMvc** y **Mockito** para endpoints REST
- Cobertura alcanzada: **~35–45%** (limitada por capas complejas sin tests previos)
- Umbrales JaCoCo configurados: `55% líneas / 10% ramas`

### Plan de Remediación Priorizado

```
Prioridad 1 — INMEDIATA (Seguridad)
  ├─ Retirar contraseña comprometida de SecurityConfig
  ├─ Revisar los 7 security hotspots
  ├─ Reemplazar SecurityUser por DTO en endpoint de registro
  └─ Retirar credenciales de application.properties

Prioridad 2 — ESTABILIZACIÓN (Arquitectura)
  ├─ Refactorizar AbstractController y AbstractService
  ├─ Migrar @Autowired field → constructor injection
  ├─ Centralizar instancia de Random
  └─ Corregir APIs deprecated

Prioridad 3 — TESTABILIDAD
  ├─ Pruebas unitarias en servicios
  ├─ Pruebas de integración de seguridad y endpoints
  └─ Quality Gate funcional en pipeline

Prioridad 4 — HIGIENE TÉCNICA
  ├─ Placeholders en logs (en vez de concatenación)
  ├─ Eliminar imports y comentarios muertos
  ├─ Extraer constantes repetidas
  └─ Modernizar anotaciones Spring (@GetMapping, @PostMapping)
```

---

## 📰 Entregable 4 — DevEx + Developer Productivity (SPACE)

> **Fecha:** 22 Mar 2026 · [Ver documento completo →](docs/DEVEX_Y_DEVELOPER_PRODUCTIVITY.md)

Análisis de la experiencia del desarrollador bajo los marcos **DevEx** y **SPACE**.

### Estado del Repositorio al Momento del Análisis

| Indicador | Valor | Lectura |
|---|---|---|
| Archivos Java `src/main` | 67 | Tamaño moderado, varias capas |
| Archivos Java `src/test` | 16 | Avance real frente al estado inicial |
| Archivos XHTML | 17 | UI no trivial — mayor costo de mantenimiento |
| CI configurado | ✅ `build.yml` | `mvn verify` + SonarQube en push/PR |
| JaCoCo umbrales | 55% / 10% | Criterio explícito de calidad mínima |

### Diagnóstico DevEx

| Dimensión | Estado | Descripción |
|---|---|---|
| 🔄 Feedback loops | ⚠️ Mixto | CI+Sonar disponibles, pero onboarding manual y documentación desactualizada |
| 🧠 Cognitive load | ❌ Alto | Spring + JSF + SQL nativo + reflexión en los mismos componentes |
| 🌊 Flow state | ❌ Limitado | Deuda estructural y fricción operativa interrumpen el foco |

### Diagnóstico SPACE

| Dimensión | Juicio |
|---|---|
| 😊 Satisfaction | No medible sin encuesta — señales mixtas en el repositorio |
| 🎯 Performance | Mejorando, pero condicionado por riesgo técnico persistente |
| 📈 Activity | Positiva en Git (Feb–Mar 2026), insuficiente como métrica aislada |
| 🤝 Communication | **Fuerte** — CONTRIBUTING.md, PRs, docs centralizados en `docs/` |
| ⚡ Efficiency | Por debajo de lo deseable — setup manual, documentación obsoleta |

### Oportunidades de Mejora Identificadas

**Corto plazo:**
- Actualizar README para reflejar el estado real (tests, CI, cobertura)
- Simplificar setup de base de datos y datos iniciales
- Retirar credenciales hardcodeadas y expuestas
- Estandarizar flujo local de validación antes de PR

**Mediano plazo:**
- Separar REST controllers de backing beans JSF
- Refactorizar clases base con alta concentración de deuda
- Migrar configuraciones y patrones legacy deprecados

**Largo plazo:**
- Rediseñar arquitectura con herencia genérica hacia composición
- Normalizar observabilidad de DevEx con encuestas periódicas
- Onboarding automatizado (setup reproducible en un comando)

---

## 📰 Entregable 5 — Deuda Técnica en Procesos (CI/CD)

> **Fecha:** 29 Mar 2026 · [Ver documento completo →](docs/DEUDA_TECNICA_EN_PROCESOS.md)

Implementación de un pipeline de CI completo con **GitHub Actions**, **SonarQube** y **Trivy**.

### Pipeline Implementado

```yaml
Trigger: push → main | pull_request
        ↓
  [Step 1] Setup JDK 8 + Maven cache
        ↓
  [Step 2] mvn -B verify  ←── Build + Unit Tests + JaCoCo
        ↓
  [Step 3] SonarQube Analysis  ←── Deuda técnica + calidad
        ↓
  [Step 4] Trivy Scanner  ←── CVEs + secrets + misconfigs
        ↓
  [Step 5] Upload SARIF → GitHub Security tab
```

### Resultados del Análisis SonarQube en CI

| Métrica | Valor |
|---|---|
| Coverage | **45.2%** |
| Duplicated lines | **0.0%** |
| Reliability rating | 4.0 (D) |
| Security rating | 5.0 (E) |
| Security hotspots | 7 |
| Maintainability (sqale) | 1.0 (A) |
| Issues totales | **76** |
| Deuda técnica estimada | **621 minutos** |

### Trivy Security Scanner

Se integró **Trivy** (Aquasecurity) en el pipeline para:

| Capacidad | Descripción |
|---|---|
| 📦 CVEs en dependencias | Escanea Spring, Maven, Java libs |
| 🔑 Secrets detection | Detecta API keys y contraseñas en código |
| ⚙️ Misconfigurations | Problemas en archivos de configuración |
| 📊 SARIF output | Resultados en GitHub Security > Code Scanning |

**Ventajas frente a OWASP:** sin rate limits, BD actualizada por Aquasecurity, ~5–10 seg de escaneo, open source Apache 2.0.

### Security Hotspots Identificados por Sonar

1. **CSRF deshabilitado** en `SecurityConfig.java`
2. **SQL dinámico** en `AbstractService.java`
3. **Pseudorandom generators** en `AbstractController.java` y `RandomUtility.java`
4. **CORS abierto** (`@CrossOrigin("*")`) en múltiples controllers

### Valor Aportado por el Pipeline

- ✅ Evita integrar cambios que rompan la compilación
- ✅ Detecta fallas en pruebas antes del merge
- ✅ Entrega evidencia automática de calidad en cada push
- ✅ Visibiliza riesgo de seguridad y confiabilidad continuamente
- ✅ Deja trazabilidad auditable en cada PR

---

## 🏗️ Estructura del Repositorio

```
customer-management-system/
│
├── 📄 README.md                        ← Documentación del proyecto original + CSDT
├── 📄 index.md                         ← Blog del curso (este archivo)
├── 📄 RefactDone_CSDT-2026.md          ← Bitácora de entregables
├── 📄 CONTRIBUTING.md                  ← Guía de contribución
├── 📄 CHANGELOG.md                     ← Historial de cambios
│
├── 📁 .github/workflows/
│   └── build.yml                       ← CI: Maven + SonarQube + Trivy
│
├── 📁 docs/
│   ├── DEUDA_TECNICA_Y_REFACTORIZACION.md  ← Entregable 1
│   ├── CODIGO_LIMPIO_+_PRACTICAS_XP.md     ← Entregable 2
│   ├── PRIMERA_ENTREGA_2026.md             ← Entregable 3
│   ├── DEVEX_Y_DEVELOPER_PRODUCTIVITY.md   ← Entregable 4
│   ├── DEUDA_TECNICA_EN_PROCESOS.md        ← Entregable 5
│   ├── PLANTILLA_ENCUESTA_DEVEX_SPACE.md   ← Encuesta DevEx
│   └── respuestas-encuesta/                ← Respuestas del equipo
│
└── 📁 src/
    ├── main/java/com/cms/
    │   ├── controller/   ← AbstractController, DashboardViewController, ...
    │   ├── service/      ← AbstractService, UnitService, ...
    │   ├── repository/   ← JPA Repositories
    │   ├── model/        ← Entidades JPA
    │   ├── configs/      ← SecurityConfig
    │   └── util/         ← RandomUtility
    └── test/java/com/cms/
        └── [100+ tests unitarios e integración]
```

---

## 🔑 Hallazgos Clave del Proyecto

> Los descubrimientos más importantes a lo largo de los 5 entregables del curso.

### Lo que encontramos al inicio

- **35+ code smells** identificados en el análisis inicial
- **Seguridad E** — vulnerabilidades críticas activas en producción
- **0% de cobertura** — sin ningún test automatizado
- **God class `AbstractController`** — 28% de toda la deuda concentrada en un solo archivo
- Credenciales en texto plano, CSRF deshabilitado, CORS abierto con `"*"`

### Lo que implementamos durante el curso

- ✅ **100+ tests unitarios** — de 0% a ~45% de cobertura
- ✅ **CI/CD con GitHub Actions** — build + tests + SonarQube + Trivy en cada PR
- ✅ **Análisis estático automatizado** — deuda visible y trazable continuamente
- ✅ **5 documentos de análisis** — deuda técnica, clean code, calidad, DevEx y procesos
- ✅ **Propuestas de refactorización concretas** con antes/después en código
- ✅ **Encuesta DevEx/SPACE** aplicada al equipo para medir experiencia del desarrollador

### La conclusión más importante

> La **Maintainability A** no compensa una **Security E**, una **Reliability D**,  
> **7 hotspots sin revisar** y **0.0% de cobertura inicial**.  
> El proyecto es funcional pero inmaduro en calidad integral.  
> La deuda está **concentrada y recuperable** — si se actúa en el orden correcto:  
> **primero seguridad → estabilidad → testabilidad → limpieza**.

---

## 📚 Stack Tecnológico Analizado

| Tecnología | Rol | Observación |
|---|---|---|
| Java 1.8 | Lenguaje base | Limitante de modernización |
| Spring Boot 1.5.7 | Framework | Versión legacy con APIs deprecated |
| Spring Security | Autenticación | Configuración insegura identificada |
| JSF / PrimeFaces | UI | Mezcla con REST genera alta carga cognitiva |
| Spring Data JPA | Persistencia | Eager fetching excesivo, IDs primitivos |
| MySQL / PostgreSQL | Base de datos | Credenciales en texto plano |
| Maven | Build | Pipeline configurado con JaCoCo |
| SonarQube Cloud | Calidad | Integrado en CI — análisis automático |
| GitHub Actions | CI/CD | Build + tests + análisis en cada push |
| Trivy | Seguridad | CVE scanning + secrets detection |
| JaCoCo | Cobertura | Umbrales 55% líneas / 10% ramas |

---

## 🔗 Referencias Bibliográficas

- **Refactoring: Improving the Design of Existing Code** — Martin Fowler
- **Clean Code: A Handbook of Agile Software Craftsmanship** — Robert C. Martin
- **Effective Java** — Joshua Bloch
- **"DevEx: What Actually Drives Productivity"** — Noda, Forsgren, Fagerholm (2023)
- **"The SPACE of Developer Productivity"** — Forsgren et al. (2021)
- **ISO/IEC 25010** — Systems and software quality models
- **OWASP Top 10 2021** — A01 → A10

---

<div align="center">

**Hecho con 🔬 por el equipo RefactDone — CSDT 2026**

[![GitHub](https://img.shields.io/badge/Repo_base-sdrahnea%2Fcustomer--management--system-181717?style=flat-square&logo=github)](https://github.com/sdrahnea/customer-management-system)
[![Org](https://img.shields.io/badge/Org-CSDT--ECI-6C63FF?style=flat-square&logo=github)](https://github.com/CSDT-ECI)

*Calidad de Software y Gestión de Deuda Técnica · 2026*

</div>
