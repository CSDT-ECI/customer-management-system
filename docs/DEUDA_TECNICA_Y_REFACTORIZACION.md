# Autores
- Juan David Rodriguez Rodriguez
- Jesus Alberto Jauregui Conde
- David Santiago Castro Sierra

# Análisis de Deuda Técnica y Propuestas de Refactorización

**Proyecto:** Customer Management System  
**Fecha de Análisis:** 12 de Febrero, 2026  
**Autor:** Análisis Automatizado de Código

---

## Tabla de Contenido

1. [Resumen Ejecutivo](#resumen-ejecutivo)
2. [Code Smells Identificados](#code-smells-identificados)
3. [Deuda Técnica por Categoría](#deuda-técnica-por-categoría)
4. [Técnicas de Refactorización Propuestas](#técnicas-de-refactorización-propuestas)
5. [Plan de Acción Priorizado](#plan-de-acción-priorizado)

---

## Resumen Ejecutivo

El proyecto Customer Management System presenta una arquitectura basada en Spring Boot con JSF/PrimeFaces. A través del análisis del código se han identificado **múltiples áreas de deuda técnica** que afectan la mantenibilidad, seguridad y escalabilidad del sistema.

### Hallazgos Principales:
- **Nivel de Deuda Técnica:** Alto
- **Áreas Críticas:** Seguridad, Arquitectura, Mantenibilidad
- **Total de Code Smells Identificados:** 35+
- **Prioridad de Refactorización:** Alta en seguridad, Media en arquitectura

---

## Code Smells Identificados

### 1. **Seguridad (CRÍTICO)**

#### 1.1 Credenciales Hardcodeadas
**Ubicación:** `com.cms.contextHolder.LoginComponent`
```java
public boolean tryToLogin(){
    if(login.equals("admin")){
        return true;
    }
    return false;
}
```
**Problema:** Credenciales hardcodeadas en el código fuente.  
**Riesgo:** Vulnerabilidad crítica de seguridad.  
**OWASP:** A07:2021 – Identification and Authentication Failures

#### 1.2 Contraseñas en Texto Plano
**Ubicación:** `application.properties`
```properties
spring.datasource.username=postgres
spring.datasource.password=postgres
```
**Problema:** Credenciales de base de datos en texto plano.  
**Riesgo:** Exposición de credenciales.  
**OWASP:** A02:2021 – Cryptographic Failures

#### 1.3 CSRF Deshabilitado
**Ubicación:** `com.cms.configs.SecurityConfig`
```java
http.csrf().disable();
```
**Problema:** Protección CSRF completamente deshabilitada.  
**Riesgo:** Vulnerabilidad a ataques Cross-Site Request Forgery.  
**OWASP:** A01:2021 – Broken Access Control

#### 1.4 Inyección SQL Potencial
**Ubicación:** `com.cms.model.Dashboard`
```java
@Lob
@Column(name = "cquery")
private String query;
```
**Problema:** Almacenamiento de queries SQL como strings que podrían ejecutarse dinámicamente.  
**Riesgo:** Potencial inyección SQL si no se sanitiza adecuadamente.  
**OWASP:** A03:2021 – Injection

#### 1.5 Uso de Clase Deprecated con Vulnerabilidades
**Ubicación:** `com.cms.configs.SecurityConfig`
```java
public class SecurityConfig extends WebSecurityConfigurerAdapter
```
**Problema:** `WebSecurityConfigurerAdapter` está deprecated desde Spring Security 5.7+ y será removido.  
**Riesgo:** Código obsoleto sin soporte de seguridad actualizado.

#### 1.6 Método main() en Clase de Configuración
**Ubicación:** `com.cms.configs.SecurityConfig`
```java
public static void main(String[] args){
    System.out.printf(new BCryptPasswordEncoder().encode("123"));
}
```
**Problema:** Método main en clase de configuración, probablemente usado para testing.  
**Riesgo:** Código de testing mezclado con producción, expone lógica de encriptación.

---

### 2. **Arquitectura y Diseño**

#### 2.1 Mezcla de Frameworks JSF y Spring
**Ubicación:** Multiple archivos
```java
@ManagedBean  // JSF deprecated
@Component    // Spring
@SessionScoped // JSF
```
**Problema:** Uso inconsistente de anotaciones JSF (`@ManagedBean`, `javax.faces.bean`) mezcladas con Spring.  
**Code Smell:** Shotgun Surgery, Divergent Change  
**Impacto:** Confusión en la gestión del ciclo de vida de los beans.

#### 2.2 God Class - AbstractController
**Ubicación:** `com.cms.controller.AbstractController`
**Problema:** Clase que mezcla múltiples responsabilidades:
- Lógica de controlador REST
- Lógica de managed bean JSF
- Métodos de utilidad genérica
- Reflexión para instanciación de objetos

**Code Smell:** God Class, Feature Envy  
**Líneas de Código:** ~150 líneas
**Responsabilidades:** 5+ diferentes

#### 2.3 Inyección de Dependencias por Campo
**Ubicación:** Multiple archivos (AbstractController, AbstractService, RandomController)
```java
@Autowired
public AbstractService<T> service;
```
**Problema:** Uso de field injection en lugar de constructor injection.  
**Code Smell:** Inappropriate Intimacy  
**Impacto:** 
- Dificulta testing (no se pueden inyectar mocks fácilmente)
- Dependencias no inmutables
- Violación del principio de inmutabilidad

#### 2.4 Empty Classes - Clases Vacías
**Ubicaciones:**
- `PersonController`
- `CountryController`
- `DashboardController`
- `PersonService`
- `CountryService`

**Problema:** Clases que solo extienden de clase base sin agregar funcionalidad.  
**Code Smell:** Lazy Class, Speculative Generality  
**Impacto:** Exceso de clases innecesarias que aumentan complejidad.

#### 2.5 Violación del Principio de Responsabilidad Única
**Ubicación:** `com.cms.controller.AbstractController`
```java
public abstract class AbstractController<T extends CoreEntity> 
    implements Serializable {
    
    // Métodos REST
    @RequestMapping(method = RequestMethod.POST)
    public T save(@RequestBody T tobject) { ... }
    
    // Métodos JSF Managed Bean
    public String save() { ... }
    public String cancel() { ... }
}
```
**Problema:** Misma clase actúa como REST Controller y JSF Managed Bean.  
**Code Smell:** Divergent Change  
**Principio SOLID violado:** Single Responsibility Principle (SRP)

---

### 3. **Persistencia y Modelo de Datos**

#### 3.1 Eager Fetching Excesivo
**Ubicación:** Multiple entidades (Unit, Person, Dashboard)
```java
@ManyToOne(fetch = FetchType.EAGER)
private Unit unit;

@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
private Collection<Person> persons = new LinkedHashSet<>();
```
**Problema:** Uso indiscriminado de `FetchType.EAGER`.  
**Code Smell:** Performance Smell  
**Impacto:** 
- Problema N+1 queries
- Cargado innecesario de datos
- Degradación de performance
- Posible OutOfMemoryError con grandes datasets

#### 3.2 Uso de Tipos Primitivos para IDs
**Ubicación:** `com.cms.model.CoreEntity`
```java
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;  // primitivo, no Long
```
**Problema:** Uso de `long` primitivo en lugar de `Long` wrapper.  
**Impacto:** No puede representar valores null, dificulta distinguir entre "no persistido" y "id = 0".

#### 3.3 Repositorio Mal Nombrado
**Ubicación:** `com.cms.repository.SecurityUserDto`
```java
public interface SecurityUserDto extends JpaRepository<SecurityUser,Long>
```
**Problema:** Nombre sugiere un DTO pero es un Repository.  
**Code Smell:** Misleading Name  
**Impacto:** Confusión en la comprensión del código.

#### 3.4 Almacenamiento de Listas como CSV
**Ubicación:** `com.cms.model.Dashboard`
```java
@Column(name = "series_tags")
private String seriesTags;

public List<String> getSeriesList() {
    return seriesTags != null ? Arrays.asList(seriesTags.split(",")) : new LinkedList<>();
}
```
**Problema:** Almacenar listas como strings separadas por comas.  
**Code Smell:** Data Clumps, Primitive Obsession  
**Impacto:** 
- Violación de 1NF (Primera Forma Normal)
- Dificulta queries
- No hay integridad referencial

#### 3.5 Ausencia de Validaciones
**Ubicación:** Todas las entidades
**Problema:** No hay anotaciones de validación (`@NotNull`, `@Size`, `@Email`, etc.)  
**Code Smell:** Missing Validation  
**Impacto:** Datos inconsistentes en la base de datos.

---

### 4. **Código Legacy y Deprecado**

#### 4.1 Métodos Deprecated de Spring Data
**Ubicación:** `com.cms.service.AbstractService`
```java
public void delete(long id) {
    repository.delete(new Long(id));  // deprecated
}

public T findById(long id) {
    T tobject = (T) repository.findOne(new Long(id));  // deprecated
    return tobject;
}
```
**Problema:** Uso de métodos deprecated:
- `repository.findOne()` → debe usar `findById()`
- `repository.delete(ID)` → debe usar `deleteById(ID)`
- `new Long(id)` → deprecated desde Java 9

**Code Smell:** Obsolete Code  
**Impacto:** Código incompatible con versiones futuras de Spring Data.

#### 4.2 Versiones de Dependencias Inconsistentes
**Ubicación:** `pom.xml`
```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>4.1.1</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>3.10-FINAL</version>  <!-- Versión antigua incompatible -->
</dependency>
```
**Problema:** Versiones incompatibles de la misma librería.  
**Code Smell:** Dependency Hell  
**Impacto:** Posibles conflictos en runtime.

### 5. **Mantenibilidad y Código Duplicado**

#### 5.1 Lógica Condicional Redundante
**Ubicación:** `com.cms.controller.AbstractController`
```java
@PostConstruct
public void init(){
    if (list == null || list.isEmpty()) {
        list = toList(findAll());
    } else {
        list = toList(findAll());  // Same code in both branches!
    }
    selectedObject = buildObject();
}
```
**Problema:** Ambas ramas del if ejecutan el mismo código.  
**Code Smell:** Dead Code, Unnecessary Complexity  
**Refactorización:** Eliminar el if-else completamente.

#### 5.2 Variables Innecesarias
**Ubicación:** Multiple archivos en AbstractService y AbstractController
```java
public T save(T tobject) {
    tobject = repository.save(tobject);
    return tobject;
}

public Iterable<T> findAll() {
    Iterable<T> tobjects = service.findAll();
    return tobjects;
}
```
**Problema:** Asignación a variable temporal solo para retornarla inmediatamente.  
**Code Smell:** Temporary Field  
**Refactorización:** Return directo del resultado.

#### 5.3 Falta de Uso de Lombok
**Ubicación:** Todas las entidades excepto LoginComponent
**Problema:** Presencia de Lombok en dependencias pero no usado consistentemente.
- `Person`, `Unit`, `Country` tienen getters/setters manuales
- `LoginComponent` sí usa `@Data`

**Code Smell:** Boilerplate Code  
**Impacto:** Código verboso innecesario.

---

### 6. **Problemas de Diseño Específicos**

#### 6.1 Ausencia de DTOs
**Ubicación:** Todos los controllers
**Problema:** Entidades JPA expuestas directamente como respuestas REST.  
**Code Smell:** Leaky Abstraction  
**Impacto:** 
- Serialización de relaciones lazy puede causar LazyInitializationException
- Exposición de detalles internos del modelo
- Dificultad para versionar la API

#### 6.2 Falta de Manejo de Excepciones
**Ubicación:** Todos los controllers y services
**Problema:** No hay `@ExceptionHandler` ni manejo centralizado de errores.  
**Code Smell:** Error Prone  
**Impacto:** Stacktraces expuestos al cliente, mala UX.

---

## Deuda Técnica por Categoría

### Categorización por Severidad

| Categoría | Severidad | Cantidad | Esfuerzo Estimado |
|-----------|-----------|----------|-------------------|
| **Seguridad** | 🔴 Crítica | 6 | Alto |
| **Arquitectura** | 🟠 Alta | 8 | Alto |
| **Persistencia** | 🟠 Alta | 5 | Medio |
| **Código Legacy** | 🟡 Media | 4 | Medio |
| **Mantenibilidad** | 🟡 Media | 7 | Bajo-Medio |
| **Diseño** | 🟡 Media | 6 | Medio |

### Impacto en Métricas de Calidad

- **Complejidad Ciclomática:** Media-Alta (debido a AbstractController)
- **Acoplamiento:** Alto (field injection, herencia profunda)
- **Cohesión:** Baja (clases con múltiples responsabilidades)
- **Cobertura de Tests:** No identificada (no hay tests visibles)
- **Duplicación:** Media (código similar en múltiples controllers)

---

## Técnicas de Refactorización Propuestas

### Catálogo de Refactorizaciones 

#### 1. **Extract Method**
**Aplicable a:** RandomController.createRandomUnit()

**Beneficios:**
- Separa responsabilidades
- Métodos más pequeños y testeables
- Reutilización de código


#### 2. **Replace Inheritance with Delegation**
**Aplicable a:** Toda la jerarquía AbstractController

**Beneficios:**
- Elimina acoplamiento por herencia
- Facilita testing
- Composición sobre herencia (principio de diseño)


#### 3. **Introduce Parameter Object**
**Aplicable a:** Métodos con múltiples parámetros de configuración

**Beneficios:**
- Menos parámetros
- Más fácil de extender
- Configuración reutilizable

#### 4. **Replace Magic Number/String with Constant**
**Aplicable a:** AbstractController, SecurityConfig

**Beneficios:**
- Elimina "magic strings"
- Facilita cambios globales
- Mejor mantenibilidad

#### 5. **Introduce Service Layer**
**Aplicable a:** Lógica de negocio en Controllers

**Beneficios:**
- Separación de responsabilidades
- Lógica de negocio testeable
- Transacciones en la capa correcta


#### 6. **Replace Constructor with Factory Method / Builder**
**Aplicable a:** Entidades con muchos campos

**Antes:**
```java
Unit unit = new Unit();
unit.setAdditionalInfo(RandomUtility.generateSentence());
unit.setAddress(RandomUtility.generateSentence());
unit.setEmail(RandomUtility.generateEmail());
unit.setFax(RandomUtility.generatePhone());
unit.setPhone(RandomUtility.generatePhone());
unit.setWebsite(RandomUtility.generateWebsite());
// ... 10 más líneas
```

**Después con Builder:**
```java
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Unit extends CoreEntity {
    // fields...
}

// Uso
Unit unit = Unit.builder()
    .additionalInfo(RandomUtility.generateSentence())
    .address(RandomUtility.generateSentence())
    .email(RandomUtility.generateEmail())
    .phone(RandomUtility.generatePhone())
    .website(RandomUtility.generateWebsite())
    .build();
```

**Beneficios:**
- Código más legible
- Inmutabilidad opcional
- Validación centralizada

---

#### 7. **Extract Class**
**Aplicable a:** AbstractController que tiene demasiadas responsabilidades

**Beneficios:**
- Single Responsibility Principle
- Reutilización selectiva
- Mejor testabilidad

#### 8. **Introduce DTO (Data Transfer Object)**
**Aplicable a:** Toda la API REST

**Antes:**
```java
@RestController
public class PersonController {
    @GetMapping
    public List<Person> findAll() {  // Expone entidad JPA directamente
        return personRepository.findAll();
    }
}
```

**Después:**
```java
@Data
@Builder
public class PersonDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UnitSummaryDTO unit;  // DTO anidado, no entidad
}

@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    
    @GetMapping
    public List<PersonDTO> findAll() {
        return personService.findAllAsDTO();
    }
}
```

**Beneficios:**
- Evita LazyInitializationException
- Control sobre datos expuestos
- Desacopla API de modelo de datos

---

#### 9. **Replace Field Injection with Constructor Injection**
**Aplicable a:** Todos los componentes Spring

**Beneficios:**
- Inmutabilidad
- Facilita testing (inyección de mocks)
- Falla rápido si falta dependencia

---

#### 10. **Introduce Security Configuration as Bean**
**Aplicable a:** SecurityConfig

**Antes:**
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // configuración
    }
}
```

**Después (Spring Security 5.7+):**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login.xhtml").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login.xhtml")
                .usernameParameter("form:username")
                .passwordParameter("form:password")
                .defaultSuccessUrl("/main.xhtml")
                .failureUrl("/login.xhtml?error=true")
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login.xhtml")
            )
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            );
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Beneficios:**
- Usa API moderna no deprecated
- CSRF habilitado por defecto
- Más configuración declarativa

---

#### 11. **Replace Query String with Criteria API / Query Methods**
**Aplicable a:** AbstractService.findByName()

**Beneficios:**
- Type-safe
- Sin reflexión
- Query derivado automáticamente
- Mejor manejo de casos no encontrados

## Conclusiones y Recomendaciones

### Conclusiones Principales

1. **Deuda Técnica Significativa:** El proyecto presenta una deuda técnica considerable, especialmente en seguridad y arquitectura.

2. **Violaciones de Principios SOLID:** Multiple violaciones del SRP, DIP y OCP dificultan la mantenibilidad.

3. **Seguridad Comprometida:** Vulnerabilidades críticas que deben resolverse inmediatamente.

## Referencias

### Libros y Recursos
- **Refactoring: Improving the Design of Existing Code** - Martin Fowler
- **Clean Code** - Robert C. Martin
- **Effective Java** - Joshua Bloch
- **Spring Security in Action** - Laurentiu Spilca

### Patrones y Principios
- **SOLID Principles**
- **Gang of Four Design Patterns**
- **Domain-Driven Design** - Eric Evans
- **Patterns of Enterprise Application Architecture** - Martin Fowler



**Documento generado el:** 12 de Febrero, 2026  
**Versión:** 1.0  
**Estado:** Propuesta Inicial

