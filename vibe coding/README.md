# Laboratorio: El efecto mariposa del código (AIDLC) 

**Autor:** David Santiago Castro Sierra  
**Programa:** Ingeniería de Sistemas  
**Institución:** Escuela Colombiana de Ingeniería (Julio Garavito)  

---

## Visión General del Proyecto
Este repositorio documenta un experimento práctico sobre la evolución y las mejores prácticas en el desarrollo de software asistido por Inteligencia Artificial Generativa. 

El objetivo principal de este laboratorio es demostrar empíricamente por qué interactuar con la IA como si fuera "magia" produce código inescalable, y cómo la adopción de metodologías rigurosas de ingeniería de software garantiza el control, la calidad y la mantenibilidad del producto final.

---

## Estructura del Laboratorio

El experimento está dividido en tres fases secuenciales, cada una con su propia documentación y código de prueba:

### [Fase 1: La Trampa del "Vibe Coding"](./Vibe%20coding/README.md)
Una exploración de los peligros de programar usando instrucciones conversacionales ambiguas. Se evidencia cómo la falta de especificaciones técnicas (Spec) lleva a la inyección innecesaria de librerías, acoplamiento de código y la creación de un "Frankenstein Code" inmanejable ante nuevos requerimientos.

### [Fase 2: Spec-Driven Development](./SDD/README.md)
El contraataque a la ambigüedad. Esta fase demuestra cómo asumir el rol de Arquitecto de Software y entregar a la IA restricciones tecnológicas estrictas e Historias de Usuario claras, resulta en un crecimiento modular, limpio y controlado de la aplicación, evitando el desperdicio de recursos (*Token Sprawl*).

### [Fase 3: Quality Gate y AI Red Teaming](./Quality%20Rate/README.md)
El cierre del ciclo de vida del desarrollo. Se invierten los roles y se utiliza la misma Inteligencia Artificial como un auditor experto en Aseguramiento de Calidad (QA) y Experiencia de Usuario (UX) para detectar, reportar y parchar vulnerabilidades lógicas y fallas de accesibilidad antes de un supuesto paso a producción.

---

## Conclusión General
La Inteligencia Artificial ha cambiado la forma en que escribimos código, pero no elimina la necesidad de la ingeniería; de hecho, la hace más crítica. 

Este laboratorio demuestra que en la era de los copilotos de IA, el valor fundamental del ingeniero de sistemas ya no es memorizar sintaxis, sino el diseño de arquitecturas robustas, la definición de restricciones inflexibles y la orquestación de la calidad. **Tú diseñas el sistema, la IA lo construye.**