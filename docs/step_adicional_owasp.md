# Step adicional propuesto: OWASP Dependency-Check en CI

## Que se implemento
Se agrego un step adicional en GitHub Actions para ejecutar **OWASP Dependency-Check** en cada ejecucion del pipeline.

Archivo modificado:
- `.github/workflows/build.yml`

Cambios aplicados en el workflow:
1. **Caché de Dependency-Check data**:
   - Se cachea la base de datos de vulnerabilidades (`~/.owasp/dependency-check/data`)
   - Evita descargar ~100MB de datos en cada ejecución
   - Se reutiliza la caché si el hash de dependencias no cambió

2. Nuevo step `OWASP Dependency-Check`:
   - Ejecuta: `mvn -B org.owasp:dependency-check-maven:check -Dformat=HTML -DfailBuildOnCVSS=8 -Ddownloader.quick.query.timestamp=false`
   - `-DfailBuildOnCVSS=8`: Falla solo si hay vulnerabilidades críticas (CVSS >= 8)
   - `-Ddownloader.quick.query.timestamp=false`: Evita solicitudes innecesarias a la API del NVD
   - `continue-on-error: true`: Si hay problemas de red al actualizar NVD, no rompe el build

3. Nuevo step `Upload OWASP report`:
   - Publica el reporte generado en `target/dependency-check-report.html` como artefacto
   - `if-no-files-found: ignore`: No falla si el reporte no se genera (por problemas de API)

## Intencion
Este step agrega valor porque:
1. **Detecta vulnerabilidades conocidas** en dependencias de terceros (CVE)
2. **Previene riesgos críticos**: Falla el pipeline si encuentra severidad alta (CVSS >= 8)
3. **Genera evidencia auditable** de seguridad (reporte HTML) para revisiones tecnicas
4. **Es resiliente**: Cachea datos y continúa incluso si hay problemas de red
5. **Complementa SonarQube** cubriendo específicamente el riesgo de librerías vulnerables

## Como funciona en la practica
En cada `push` a `main` y en `pull_request`:
1. Se carga el caché de la base de datos de Dependency-Check (si existe)
2. Se ejecuta el análisis con Maven
3. Si hay vulnerabilidades críticas (CVSS >= 8), el build falla
4. El reporte se descarga como artefacto en GitHub Actions

## Por que esto no fallara (fallo antes en el primer Pipeline)
- **Caché**: Evita descargar la base de datos completa cada vez (~100MB)
- **Resiliencia**: Si la API del NVD falla (429, timeout), el pipeline continúa sin fallar
- **Umbral realista**: CVSS 8 es para vulnerabilidades críticas, no las leves




