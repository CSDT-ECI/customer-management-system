# Reporte de Laboratorio: Fase 3 - Quality Gate (Orquestación) 

**Autor:** David Santiago Castro Sierra  
**Programa:** Ingeniería de Sistemas  
**Institución:** Escuela Colombiana de Ingeniería (Julio Garavito)  

---

## Introducción
Este documento registra la tercera y última fase del ciclo AIDLC (Artificial Intelligence Development Life Cycle). Tras haber construido una base sólida mediante el *Spec-Driven Development* (Fase 2), se somete el código a un **Quality Gate**. 

En esta fase, se utiliza la técnica de **AI Red Teaming**, donde se le pide a la misma Inteligencia Artificial que cambie de rol, actúe como un auditor experto en QA (Aseguramiento de Calidad) y UX (Experiencia de Usuario), y ataque su propio código para encontrar vulnerabilidades y fallos de usabilidad.

---

## Auditoría de Código (QA & UX)

### El Prompt de Red Teaming
> *"Cambia de rol. Ahora eres un experto en Aseguramiento de Calidad (QA) y Experiencia de Usuario (UX). Analiza mi código anterior. Encuentra posibles bugs si el usuario ingresa letras en vez de números, o si hay problemas de accesibilidad (contraste). Dime qué corregirías y refactoriza solo la parte afectada."*

### Hallazgos del Auditor (La IA evaluándose a sí misma)

1. **Fallas Críticas de Validación (Bugs lógicos):**
   * El atributo `type="number"` en HTML no es suficiente. Permitía el ingreso de espacios en blanco, números negativos (que no tienen sentido para horas de vuelo o kilómetros) y caracteres inválidos que el motor de JS transformaba en `NaN` (Not a Number).
   * La validación original solo verificaba si el campo estaba "vacío", no si el dato era lógicamente válido.

2. **Fallas de Accesibilidad (UX / a11y):**
   * **Navegación por teclado:** No existía un estado visible (`:focus`) para los usuarios que navegan sin ratón.
   * **Lectores de pantalla:** El mensaje de resultado/error no tenía el atributo `aria-live`, por lo que las tecnologías de asistencia no anunciaban el cambio dinámico en la pantalla.
   * **Feedback visual:** Al ocurrir un error, no se marcaba visualmente qué campo específico lo estaba causando.

---

## Refactorización (Antes vs. Después)

En lugar de reescribir toda la aplicación, la IA aplicó parches quirúrgicos únicamente en las áreas afectadas:

### 1. Mejoras en el HTML (Restricciones y Accesibilidad)
Se pasó de un *input* básico a uno con restricciones matemáticas y de accesibilidad.

* **Antes:** `<input type="number" id="kilometros" />`
* **Después:** `<input type="number" id="kilometros" min="0" step="any" required />`
* **Accesibilidad añadida:** Al contenedor del resultado se le agregó `<p id="resultado" aria-live="polite"></p>` para que los lectores de pantalla notifiquen al usuario cuando aparezca el cálculo.

### 2. Mejoras en el CSS (Feedback Visual)
Se agregaron clases específicas para mejorar la experiencia de usuario sin alterar el diseño base.
* Se implementó un borde azul (`outline: 2px solid #1f6feb;`) para el estado `:focus` de inputs y botones.
* Se creó la clase `.input-error` (con su respectiva variante para el *Modo Oscuro*) que pinta un borde rojo (`#d93025`) en los campos que fallan la validación.

### 3. Mejoras en el JavaScript (Lógica Robusta)
El bloque de cálculo pasó de ser una suma ingenua a un proceso de validación estricto.

* **Limpieza y Feedback:** Se creó una función `limpiarErrores()` para reiniciar el estado visual antes de cada cálculo.
* **Validación Fuerte:** Se introdujo la función `obtenerNumeroValido(input)` que:
  1. Limpia espacios en blanco (`trim`).
  2. Verifica que sea un número finito (`Number.isFinite`).
  3. Verifica que no sea negativo (`numero < 0`).
  4. Si falla, le agrega dinámicamente la clase `.input-error` al campo exacto y detiene el proceso.
