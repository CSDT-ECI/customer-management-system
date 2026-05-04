<div align="center">

<a href="https://csdt-eci.github.io/customer-management-system/" target="_blank">
   <img src="https://img.shields.io/badge/Ver%20Sitio%20Web%20-%20CSDT%20Project-blue?style=for-the-badge&logo=githubpages&logoColor=white" alt="Ir al sitio web" />
</a>

# 🗂️ Customer Management System

**A full-stack enterprise solution for managing clients, people, and custom business dashboards.**

[![Java](https://img.shields.io/badge/Java-1.8-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-1.5.7-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Build_Tool-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE.md)

</div>

---

## 📖 About the Project

**Customer Management System (CMS)** helps you manage the business activity of your entity with ease. It provides:

- 👥 **Client & Person Management** — Full CRUD operations for all entities
- 📊 **Custom Dashboards** — Build dashboards using native SQL scripts
- 🔐 **Secure Authentication** — Powered by Spring Security
- 🗄️ **Dual Database Support** — Compatible with MySQL and PostgreSQL

---

## 📋 Table of Contents

1. [Getting Started](#-getting-started)
   - [Prerequisites](#11-prerequisites)
   - [Installing](#12-installing)
2. [Running the Tests](#-running-the-tests)
3. [Deployment](#-deployment)
4. [Built With](#-built-with)
5. [Issues & Support](#-issues--support)
6. [Contributing](#-contributing)
7. [Versioning](#-versioning)
8. [Authors](#-authors)
9. [License](#-license)
10. [Donation](#-donation)
11. [🎓 CSDT 2026 — Course Work](#-csdt-2026--calidad-de-software-y-gestión-de-deuda-técnica)

---

## 🚀 Getting Started

Clone or download a copy of this project to get started:

```bash
git clone https://github.com/sdrahnea/customer-management-system.git
cd customer-management-system
```

### 1.1 Prerequisites

Make sure you have the following installed:

| Tool | Version | Link |
|------|---------|------|
| Java JDK | 1.8+ | [Download](https://www.java.com/en/download/) |
| Maven | 3.x | [Download](https://maven.apache.org/download.cgi) |
| MySQL **or** PostgreSQL | Latest | [MySQL](https://www.mysql.com/) / [PostgreSQL](https://www.postgresql.org/) |

### 1.2 Installing

**Step 1 — Create the database**

```sql
CREATE DATABASE cms;
```

**Step 2 — Populate with initial data**

Execute the following `.sql` files in order:

```
chart_type.sql
country.sql
data.sql
first_name.sql
last_name.sql
unit_industry.sql
unit_type.sql
```

> **⚠️ Note for MySQL 8.0.4+:** Run the following query to fix authentication issues:
> ```sql
> ALTER USER '${USER}'@'localhost' IDENTIFIED WITH mysql_native_password BY '${PASSWORD}';
> -- Replace ${USER} and ${PASSWORD} with your actual credentials.
> ```

**Step 3 — Build the project**

```bash
mvn clean compile package
```

Expected output on success:

```
[INFO] BUILD SUCCESS
[INFO] Total time: 8.183 s
```

---

## 🧪 Running the Tests

> This project currently does not include automated tests. Manual testing is performed through the UI after deployment.

---

## ⚙️ Deployment

Once the JAR is built, run the application with:

```bash
java -jar target/customer-management-system-0.0.2-SNAPSHOT.jar
```

Then open your browser and navigate to:

```
http://localhost:8081/cms/login.xhtml
```

**Default credentials:**

| Field | Value |
|-------|-------|
| Username | `admin` |
| Password | `123` |

---

## 🛠️ Built With

| Technology | Purpose |
|-----------|---------|
| [Java](https://www.java.com/en/download/) | Core programming language |
| [Spring Boot](https://spring.io/projects/spring-boot) | Application framework |
| [Spring Security](https://spring.io/projects/spring-security) | Authentication & authorization |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa) | Data access layer |
| [PrimeFaces](https://www.primefaces.org/) | JSF UI component library |
| [MySQL](https://www.mysql.com/) | Primary relational database |
| [PostgreSQL](https://www.postgresql.org/) | Alternative relational database |
| [Maven](https://maven.apache.org/) | Build & dependency management |

---

## 🐛 Issues & Support

Found a bug or need help?

- 📌 [Open an issue](../../issues) in the project's repository
- 💼 Contact via [LinkedIn](https://www.linkedin.com/in/sergiu-drahnea/)
- 📧 See the [LICENSE.md](LICENSE.md) for email contact information

---

## 🤝 Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

---

## 🔖 Versioning

We use [SemVer](http://semver.org/) for versioning. Check the [tags on this repository](../../tags) for all available versions.

---

## 👤 Authors

- **Sergiu Drahnea** — *Initial work* — [LinkedIn](https://www.linkedin.com/in/sergiu-drahnea/)

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE.md](LICENSE.md) file for details.

---

## 💙 Donation

If this project was useful to you, consider supporting the author:

[![PayPal](https://img.shields.io/badge/Donate-PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white)](https://www.paypal.me/sdrahnea)

---

---

## 🎓 CSDT 2026 — Calidad de Software y Gestión de Deuda Técnica

> Este apartado documenta el trabajo académico desarrollado sobre este repositorio como proyecto base durante el curso **Calidad de Software y Gestión de Deuda Técnica (CSDT)**.

<div align="center">

[![Curso](https://img.shields.io/badge/Curso-CSDT_2026-6C63FF?style=for-the-badge)](.)
[![Estado](https://img.shields.io/badge/Estado-Completado-success?style=for-the-badge)](.)

</div>

### 👨‍💻 Integrantes del equipo

| Nombre | GitHub |
|--------|--------|
| David Santiago Castro Sierra | [@daviidc29](https://github.com/daviidc29) |
| Jesus Alberto Jauregui Conde | [@JesusJC15](https://github.com/JesusJC15) |
| Juan David Rodriguez Rodriguez | [@Enigmus12](https://github.com/Enigmus12) |

---

### 📚 Entregables del curso

#### Entregable 1 — Refactoring + Code Smells
> Identificación y análisis de malos olores en el código base, propuesta de refactorizaciones y aplicación de patrones limpios.

📄 [Ver documento completo → DEUDA_TECNICA_Y_REFACTORIZACION.md](docs/DEUDA_TECNICA_Y_REFACTORIZACION.md)

---

#### Entregable 2 — Clean Code + XP Practices
> Aplicación de principios de código limpio y prácticas de Extreme Programming (XP) sobre el sistema.

📄 [Ver documento completo → CODIGO_LIMPIO_+_PRACTICAS_XP.md](docs/CODIGO_LIMPIO_+_PRACTICAS_XP.md)

---

#### Entregable 3 — Primera Entrega Sem06
> Primera entrega formal del semestre con análisis integral del proyecto.

📄 [Ver documento completo → PRIMERA_ENTREGA_2026.md](docs/PRIMERA_ENTREGA_2026.md)

---

#### Entregable 4 — DevEx + Developer Productivity
> Evaluación de la experiencia del desarrollador (DevEx) y análisis de productividad sobre el proyecto base.

📄 [Ver documento completo → DEVEX_Y_DEVELOPER_PRODUCTIVITY.md](docs/DEVEX_Y_DEVELOPER_PRODUCTIVITY.md)

---

#### Entregable 5 — Deuda Técnica en Procesos
> Análisis de la deuda técnica a nivel de procesos de desarrollo, metodología y flujos de trabajo.

📄 [Ver documento completo → DEUDA_TECNICA_EN_PROCESOS.md](docs/DEUDA_TECNICA_EN_PROCESOS.md)

---

### 🗓️ Bitácora del curso

| Fecha | Actividad | Documento | Estado |
|-------|-----------|-----------|--------|
| 2026-02-12 | Refactoring + Code Smells | [DEUDA_TECNICA_Y_REFACTORIZACION.md](docs/DEUDA_TECNICA_Y_REFACTORIZACION.md) | ✅ Hecho |
| 2026-02-19 | Clean Code + XP Practices | [CODIGO_LIMPIO_+_PRACTICAS_XP.md](docs/CODIGO_LIMPIO_+_PRACTICAS_XP.md) | ✅ Hecho |
| 2026-03-15 | Primera Entrega | [PRIMERA_ENTREGA_2026.md](docs/PRIMERA_ENTREGA_2026.md) | ✅ Hecho |
| 2026-03-22 | DevEx + Developer Productivity | [DEVEX_Y_DEVELOPER_PRODUCTIVITY.md](docs/DEVEX_Y_DEVELOPER_PRODUCTIVITY.md) | ✅ Hecho |
| 2026-03-29 | Deuda técnica en procesos | [DEUDA_TECNICA_EN_PROCESOS.md](docs/DEUDA_TECNICA_EN_PROCESOS.md) | ✅ Hecho |

---

### 🗂️ Estructura del repositorio

```text
.
├── .github/
├── .vscode/
├── docs/
│   ├── respuestas-encuesta/
│   ├── CODIGO_LIMPIO_+_PRACTICAS_XP.md
│   ├── DEUDA_TECNICA_EN_PROCESOS.md
│   ├── DEUDA_TECNICA_Y_REFACTORIZACION.md
│   ├── DEVEX_Y_DEVELOPER_PRODUCTIVITY.md
│   ├── PLANTILLA_ENCUESTA_DEVEX_SPACE.md
│   └── PRIMERA_ENTREGA_2026.md
├── src/
├── pom.xml
└── README.md
```

### 📝 Descripción general del trabajo académico

Este repositorio contiene el trabajo desarrollado durante el curso **Calidad de Software y Gestión de Deuda Técnica** sobre el proyecto base **Customer Management System**. A lo largo del curso se realizaron distintos entregables orientados a identificar, analizar y documentar problemas de calidad, mantenibilidad, productividad y deuda técnica presentes en el sistema.

Cada documento en la carpeta `docs/` corresponde a una entrega específica y reúne el análisis realizado por el equipo, junto con observaciones, hallazgos, propuestas de mejora y evidencias del trabajo realizado en clase.

---

<div align="center">
<sub>Hecho por el equipo RefactDone CSDT 2026</sub>
</div>