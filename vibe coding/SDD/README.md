# Reporte de Laboratorio: Fase 2 - Spec-Driven Development 

**Autor:** David Santiago Castro Sierra  
**Programa:** Ingeniería de Sistemas  
**Institución:** Escuela Colombiana de Ingeniería (Julio Garavito)  

---

## Introducción
Este documento registra la segunda fase del experimento de co-creación con Inteligencia Artificial: el **Spec-Driven Development** (Desarrollo Guiado por Especificaciones). A diferencia del "Vibe Coding", aquí se asume el rol de Arquitecto de Software, entregando a la IA un contexto tecnológico estricto, restricciones arquitectónicas claras e Historias de Usuario precisas antes de escribir una sola línea de código. 

El objetivo es demostrar cómo un "Spec" bien definido evita que la IA alucine dependencias y construya código inescalable.

---

## Evolución del Proyecto (Control vs. Caos)

### Versión 1: El Mega-Prompt y la Base Sólida (`index.html`)
> **Historia de Usuario 1:** Solicitud de 3 campos numéricos (Auto, Vuelo, Electricidad), una fórmula de cálculo exacta `(Suma * 0.5)`, validación de campos vacíos y restricciones de usar únicamente Vanilla JS, HTML semántico y CSS puro en un solo archivo.

**Análisis de la Construcción:**
* **Obediencia Arquitectónica:** La IA respetó la restricción de no usar frameworks ni CDNs externos. El código entregado es una SPA (Single Page Application) completamente autocontenida y ligera.
* **Separación de Responsabilidades:** Aunque todo está en un mismo archivo, el HTML, el bloque `<style>` y la lógica dentro del `<script>` están claramente delimitados y son legibles.
* **Funcionalidad Exacta:** La validación de campos y el cálculo matemático se implementaron exactamente como se definió en la regla de negocio, sin inventar interfaces complejas ni gráficas no solicitadas.

### Versión 2: Crecimiento Modular Incremental (`indexv2.html`)
> **Historia de Usuario 2:** Implementar un botón "Cambiar a Modo Oscuro" que únicamente agregue la clase CSS `.dark-mode` al `body`, usando los colores hexadecimales `#222` y `#fff`.

**Análisis de la Iteración:**
* **Cero Efecto Mariposa:** A diferencia de la Fase 1, pedir un cambio visual no destruyó el diseño anterior. La IA simplemente añadió el botón al HTML, la regla `.dark-mode` al CSS y un Event Listener de 3 líneas en el JavaScript.
* **Mantenibilidad:** El componente creció de forma modular. La lógica del cálculo de la huella de carbono se mantuvo intacta y aislada de la nueva lógica de la interfaz de usuario.

---

## Análisis y Reflexión del Equipo

### ¿Por qué dar un 'Spec' previo evita el "Token Sprawl"?
El *Token Sprawl* (o gasto innecesario de tokens y lógica) ocurre cuando la IA tiene que "adivinar" lo que el usuario quiere. Si le pides algo "bonito", la IA gastará tokens importando librerías como Bootstrap o Chart.js, generando animaciones complejas y asumiendo reglas de negocio para intentar complacerte. 

Al proporcionar un **Spec**, se cierran los caminos de la ambigüedad. La IA no gasta tokens tomando decisiones de diseño o arquitectura; invierte todos sus recursos exclusivamente en traducir tu lógica de negocio a código eficiente.

### Lección Principal
**La Especificación ES el Nuevo Código.** En la era de la IA generativa, el valor del ingeniero no radica en memorizar sintaxis, sino en el diseño del sistema, la definición de arquitecturas, el establecimiento de restricciones y la redacción de reglas de negocio inflexibles. **Tú diseñas la solución, el Copiloto simplemente la tipea.**