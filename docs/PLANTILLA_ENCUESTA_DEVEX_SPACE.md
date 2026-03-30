# Plantilla de Encuesta DevEx + SPACE

## Propósito

Esta encuesta breve permite capturar la dimensión perceptual de la experiencia del desarrollador y complementar la evidencia técnica del repositorio. Su objetivo no es evaluar personas, sino identificar puntos de fricción y oportunidades de mejora en el proceso de desarrollo.

## Instrucciones

- Escala Likert sugerida para preguntas 1 a 8:
  - 1 = Totalmente en desacuerdo
  - 2 = En desacuerdo
  - 3 = Neutral
  - 4 = De acuerdo
  - 5 = Totalmente de acuerdo
- Preguntas 9 y 10: selección múltiple o respuesta corta.
- Aplicación sugerida: mensual o al cierre de cada iteración importante.
- Tiempo estimado de respuesta: 3 a 5 minutos.

---

## Bloque A. DevEx

### Feedback loops

1. Puedo validar un cambio local en el proyecto con rapidez razonable.
2. El flujo de pruebas, build y CI me da retroalimentación útil y oportuna.
3. La documentación del proyecto me permite entender con claridad cómo correr y validar el sistema.

### Cognitive load

4. Me resulta fácil identificar dónde debo hacer un cambio en el código.
5. La estructura actual del proyecto reduce, en lugar de aumentar, mi carga mental al desarrollar.
6. La mezcla de tecnologías y capas del proyecto es comprensible para mí.

### Flow state

7. Puedo trabajar con foco sostenido en este proyecto sin interrupciones técnicas frecuentes.
8. Siento que el proyecto me permite avanzar sin demasiado retrabajo o bloqueos evitables.

---

## Bloque B. SPACE

### Satisfaction and well-being

9. ¿Cómo describirías tu experiencia general desarrollando en este proyecto?

- Muy negativa
- Negativa
- Neutral
- Positiva
- Muy positiva

### Communication and collaboration

10. ¿Cuál de estos factores ayuda más a tu trabajo en equipo dentro del proyecto?

- Documentación compartida
- Pull requests y revisiones
- Comunicación directa entre integrantes
- Bitácora y trazabilidad de entregables
- Otro

### Preguntas opcionales de apoyo

- ¿Cuál es hoy la principal fricción técnica o de proceso para desarrollar en el proyecto?
- ¿Qué mejora concreta tendría el mayor impacto positivo en tu experiencia como desarrollador?

---

## Mapeo de la encuesta a los frameworks

| Pregunta | Dimensión |
| :-- | :-- |
| 1, 2, 3 | DevEx - Feedback loops |
| 4, 5, 6 | DevEx - Cognitive load |
| 7, 8 | DevEx - Flow state |
| 9 | SPACE - Satisfaction and well-being |
| 10 | SPACE - Communication and collaboration |
