# Deuda técnica en procesos

## Autores

- Juan David Rodriguez Rodriguez
- Jesus Alberto Jauregui Conde
- David Santiago Castro Sierra

# 1. Build, Unit Test y Code Analysis con SonarQube

## Qué se implementó

Se configuró un flujo de **Integración Continua (CI)** en **GitHub Actions** para ejecutar validaciones automáticas sobre el proyecto en cada `push` a `main` y en cada `pull_request`.

Archivo modificado:

* `.github/workflows/build.yml`

Este flujo ya cubre los tres elementos mínimos solicitados en el curso:

1. **Build**
2. **Unit Test**
3. **Code Analysis reportado en Sonar**

Aunque en el workflow estas validaciones se ejecutan dentro de la misma cadena de Maven y del análisis de Sonar, funcionalmente el pipeline sí está validando compilación, pruebas y análisis estático del proyecto.

## Cómo se ejecuta en el workflow

### Step 1: Preparación del entorno

El pipeline realiza primero la descarga del repositorio, configura **JDK 8** y usa caché para acelerar dependencias de Maven y paquetes de Sonar.

### Step 2: Build y Unit Test

La ejecución de Maven verifica que el proyecto compile correctamente y que las pruebas unitarias se ejecuten dentro del ciclo de vida de construcción.

Esto permite detectar de forma automática:

* errores de compilación,
* fallas en pruebas,
* problemas que impidan empaquetar o validar el proyecto.

### Step 3: Code Analysis con SonarQube / SonarCloud

Después de la validación del proyecto, se lanza el análisis estático con Sonar usando el token configurado en `SONAR_TOKEN`.

Comando principal usado en el pipeline:

```bash
mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=CSDT-ECI_customer-management-system
```

### Qué evidencia dejó el análisis de Sonar

El análisis realizado sobre el proyecto mostró que el repositorio sí está siendo inspeccionado automáticamente por Sonar y que el pipeline genera información útil para controlar deuda técnica, calidad y riesgos.

Resultados principales del análisis:

* **Coverage:** 45.2%
* **Duplicated lines density:** 0.0%
* **Reliability rating:** 4.0
* **Security rating:** 5.0
* **Security hotspots:** 7
* **Maintainability rating (sqale):** 1.0

## Interpretación del resultado

Estos resultados permiten concluir que el pipeline no solo verifica que el proyecto “corra”, sino que también entrega una medida objetiva del estado de calidad del código.

Hallazgos relevantes del reporte:

* Existen **76 issues abiertos** en total.
* La deuda técnica estimada es de **621 minutos**.
* La mayor parte de los hallazgos corresponde a **code smells**, pero también existen **bugs** y **vulnerabilities**.
* Los archivos con mayor concentración de problemas son `AbstractController.java` y `AbstractService.java`, lo que sugiere que son puntos prioritarios de refactorización.

Distribución general de issues:

* **69 Code Smells**
* **4 Vulnerabilities**
* **3 Bugs**

Distribución por severidad:

* **34 Minor**
* **30 Major**
* **10 Critical**
* **2 Blocker**

## Hallazgos más importantes encontrados por Sonar

Entre los hallazgos más delicados del análisis se encuentran:

1. **Contraseña comprometida en `SecurityConfig.java`**

   * Sonar marca que una contraseña debe ser revocada y cambiada.
   * Este es uno de los hallazgos más críticos porque impacta directamente la seguridad.

2. **Uso de datos controlados por el usuario en logs**

   * En `AbstractController.java`, Sonar detecta riesgo por registrar información que puede venir manipulada desde una petición HTTP.

3. **Exposición de entidades persistentes**

   * En `MainRestController.java`, Sonar recomienda no usar directamente una entidad persistente y reemplazarla por un DTO o un POJO simple.

4. **Problemas de confiabilidad y diseño**

   * Reutilización incorrecta de `Random`.
   * Inyección por campos en lugar de constructor.
   * Literales repetidos.
   * Bloques comentados y código mejorable en controladores y servicios.

## Security Hotspots identificados

Además de los issues, Sonar reportó **7 security hotspots** que requieren revisión manual. Los más importantes son:

* Revisión del **deshabilitado de CSRF** en `SecurityConfig.java`
* Revisión de **SQL dinámico** en `AbstractService.java`
* Revisión del uso de **pseudorandom generators** en `AbstractController.java` y `RandomUtility.java`
* Revisión de configuración **CORS** en varios controladores

Esto es importante porque un hotspot no siempre significa una vulnerabilidad confirmada, pero sí un punto que debe justificarse o corregirse.

## Valor que aporta 

La incorporación de Build, Unit Test y SonarQube dentro del flujo de CI aporta valor real al proyecto porque:

* evita integrar cambios que rompan la compilación,
* permite detectar fallas tempranas en pruebas,
* entrega evidencia automática de calidad del código,
* ayuda a identificar deuda técnica antes de que crezca,
* visibiliza riesgos de seguridad y confiabilidad,
* deja trazabilidad en cada `push` y `pull request`.



# 2. Trivy Security Scanner en CI

## Que se implemento
Se agregó **Trivy** como step en GitHub Actions para ejecutar escaneo de vulnerabilidades en cada push y pull request.

Archivo modificado:
- `.github/workflows/build.yml`

## Cambios en el workflow

### Step 1: Trivy Scanner
```yaml
- name: Trivy vulnerability scanner
  uses: aquasecurity/trivy-action@master
  with:
    scan-type: 'fs'           # Full filesystem scan
    scan-ref: '.'             # Root del repositorio
    format: 'sarif'           # Formato estándar de secretos/vulnerabilidades
    output: 'trivy-results.sarif'
```

### Step 2: Integración con GitHub Security
```yaml
- name: Upload Trivy results to GitHub Security
  uses: github/codeql-action/upload-sarif@v3
  with:
    sarif_file: 'trivy-results.sarif'
```

Los resultados aparecen automáticamente en:
- **Security > Code scanning results** en GitHub
- **Pull Requests** (si hay vulnerabilidades en cambios)

## Por qué Trivy

1. **Sin rate limits** - A diferencia de OWASP/NVD API
2. **BD de vulnerabilidades actualizada** - Aquasecurity lo mantiene
3. **Multi-formato** - Escanea dependencies, código IaC, imágenes Docker, etc.
4. **Integración nativa** - Funciona directo en GitHub Security
5. **Rápido** - ~5-10 segundos de escaneo
6. **Open Source** - FOSS bajo licencia Apache

## Que detecta

- **Vulnerabilidades en dependencias** (Java, npm, Python, etc.)
- **Secrets** (API keys, contraseñas en el código)
- **Misconfigurations** (problemas en config files)
- **IaC issues** (Terraform, CloudFormation, etc.)

## Cómo ver los resultados

### En GitHub
1. Ir a el repo
2. Click en **Security** tab
3. **Code scanning** muestra todos los hallazgos
4. Click en cada uno para detalles

### Localmente
```bash
# Instalar Trivy
curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b /usr/local/bin

# Ejecutar scanner
trivy fs . --format sarif --output trivy-results.sarif

# Abrir reporte (si está en tu máquina)
trivy fs .
```

## Valor que aporta

1. **Detecta CVEs** en dependencias de manera confiable
2. **Previene secrets** (API keys, contraseñas) en commits
3. **Evidencia auditable** visible en GitHub Security
4. **Integración automática** sin configuración adicional
5. **Complementa SonarQube** en seguridad de dependencias







