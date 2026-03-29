# Step adicional propuesto: OWASP Dependency-Check en CI

## Que se implemento
Se agrego un step adicional en GitHub Actions para ejecutar **OWASP Dependency-Check** en cada ejecucion del pipeline.

Archivo modificado:
- `.github/workflows/build.yml`

Cambios aplicados en el workflow:
1. Nuevo step `OWASP Dependency-Check`:
   - Ejecuta: `mvn -B org.owasp:dependency-check-maven:check -Dformat=HTML -DfailBuildOnCVSS=7`
2. Nuevo step `Upload OWASP report`:
   - Publica el reporte generado en `target/dependency-check-report.html` como artefacto de GitHub Actions.

## Intencion (por que agrega valor)
Este step agrega valor porque:
1. Detecta vulnerabilidades conocidas en dependencias de terceros (CVE).
2. Previene que se integren cambios con riesgo alto, ya que puede fallar el pipeline cuando la severidad es alta (`CVSS >= 7`).
3. Genera evidencia auditable de seguridad (reporte HTML) para revisiones tecnicas y academicas.
4. Complementa SonarQube, cubriendo especificamente el riesgo de librerias vulnerables.

## Como funciona en la practica
En cada `push` a `main` y en `pull_request`:
1. Se compila y prepara el proyecto.
2. Se ejecuta OWASP Dependency-Check.
3. Si encuentra vulnerabilidades con severidad mayor o igual a 7, el build puede fallar.
4. El reporte se guarda como artefacto para inspeccion manual.

## Evidencia para mostrar en la entrega
1. Ir a una ejecucion de GitHub Actions.
2. Verificar que aparece el step `OWASP Dependency-Check`.
3. Descargar el artefacto `owasp-dependency-check-report`.
4. Mostrar el archivo HTML con el resumen de hallazgos.

## Nota tecnica
Si en una iteracion futura se requiere menos sensibilidad, se puede ajustar el umbral de severidad cambiando el valor de:
- `-DfailBuildOnCVSS=7`

Por ejemplo, para ser mas estricto usar 6, o menos estricto usar 8.
