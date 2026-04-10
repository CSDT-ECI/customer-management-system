# Reporte de Laboratorio: La Trampa del "Vibe Coding"

**Autor:** David Santiago Castro Sierra  
**Programa:** Ingeniería de Sistemas  
**Institución:** Escuela Colombiana de Ingeniería (Julio Garavito)  

---

## Introducción
Este documento registra el experimento de "Vibe Coding": la práctica de programar dándole instrucciones conversacionales y ambiguas a una Inteligencia Artificial, sin proporcionar una arquitectura o especificación técnica clara (Spec). A través de tres iteraciones, se evidencia cómo la falta de directrices transforma un prototipo limpio en un código difícil de mantener.

---

## Evolución del Proyecto

### Versión 1: El Prompt Perezoso (`index.html`)
> **Prompt:** *"Hazme una página web bonita para una calculadora de huella de carbono. Que calcule cosas y se vea moderna."*

**Análisis del Resultado:**
* El resultado inicial es sorprendentemente bueno y visualmente atractivo.
* **Arquitectura limpia:** El archivo no depende de librerías externas para su interfaz, utilizando únicamente HTML, CSS puro (con variables `:root` bien definidas) y JavaScript integrado.
* **La trampa:** Parece mágico. La IA asume la fórmula matemática y estructura el DOM a su conveniencia, pero al no tener una base sólida, está a un paso de romperse.

### Versión 2: El Giro Inesperado (`indexv2.html`)
> **Prompt:** *"Ahora haz que los cálculos se guarden en una tabla abajo, añade una gráfica interactiva súper profesional y haz que los colores dependan del resultado. Ah, y que se pueda descargar como PDF profesional."*

**Análisis del Desastre (El Efecto Mariposa):**
* **Inyección de Librerías:** Ante la ambigüedad de "súper profesional", la IA inyectó repentinamente tres librerías pesadas a través de CDNs: `Chart.js`, `html2canvas` y `jspdf`.
* **Acoplamiento del Diseño:** Para cumplir con la regla de los colores dinámicos, la IA alteró drásticamente el CSS, creando selectores globales como `body[data-theme="low"]` y `body[data-theme="high"]` que ahora controlan la paleta de toda la aplicación de forma forzada.
* La complejidad del archivo se disparó, mezclando lógica de renderizado de gráficas con la manipulación del DOM.

### Versión 3: El Colapso (Frankenstein Code) (`indexv3.html`)
> **Prompt:** *"No me gusta el gráfico, quítalo y haz que sea minimalista en una sola tarjeta, pero mantén la descarga de PDF. Además, ahora los cálculos deben ser mensuales estimamos por 12 meses, no por año. Arréglalo rápido."*

**Análisis del Desastre Final:**
* **Pérdida de Contexto:** La IA eliminó la importación de `Chart.js`, pero conservó las pesadas dependencias de `html2canvas` y `jspdf`. 
* **Alucinaciones de Arquitectura:** Al pedirle que todo estuviera en "una sola tarjeta minimalista", la IA comprimió la estructura en una nueva clase `.single-layout`. El código ahora es un parche sobre otro parche.
* **Lógica Rota:** Cambiar de estimaciones anuales a mensuales obligó a la IA a reescribir funciones sobre la marcha, generando una "sopa" de JavaScript donde la recolección de datos, los cálculos matemáticos y la generación del PDF ocurren sin ninguna separación de responsabilidades.

---

## Conclusiones
1. **Librerías Sorpresa:** Sin un *Spec* estricto, la IA resolverá tus problemas importando dependencias masivas que probablemente no necesitas.
2. **El Espejismo de la Primera Iteración:** Que la primera versión funcione perfecto no significa que la base sea escalable.
3. **Frankenstein Code:** Cada instrucción ambigua que busca corregir el diseño o la lógica destruye la arquitectura previa, dejando "código fantasma" y estructuras acopladas que son imposibles de refactorizar manualmente.