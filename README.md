# ⚙️ Backend – Laboratorio Clínico (Microservicios)

Backend del sistema de gestión de laboratorio clínico, construido con **Spring Boot 3/4** y arquitectura de **microservicios independientes**. Cada servicio expone una API REST protegida con JWT y se conecta a una base de datos **Oracle Autonomous Database** en la nube (Oracle Cloud).

---

## 📋 Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Arquitectura de Microservicios](#arquitectura-de-microservicios)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Estructura del Repositorio](#estructura-del-repositorio)
- [Microservicio: Usuarios (`/usuario`)](#microservicio-usuarios)
- [Microservicio: Laboratorios (`/laboratorio`)](#microservicio-laboratorios)
- [Microservicio: Resultados (`/microservicio-resultados`)](#microservicio-resultados)
- [Seguridad y Autenticación JWT](#seguridad-y-autenticación-jwt)
- [Base de Datos Oracle Cloud](#base-de-datos-oracle-cloud)
- [Requisitos Previos](#requisitos-previos)
- [Ejecución Local (sin Docker)](#ejecución-local-sin-docker)
- [Despliegue con Docker Compose](#despliegue-con-docker-compose)
- [Variables de Entorno](#variables-de-entorno)
- [Usuario Administrador por Defecto](#usuario-administrador-por-defecto)
- [Documentación de la API (Swagger)](#documentación-de-la-api-swagger)
- [Pruebas](#pruebas)

---

## Descripción General

El sistema está compuesto por **tres microservicios** completamente independientes entre sí. Cada uno tiene su propio contexto de dominio, su propio puerto y su propio `Dockerfile`. Se levantan en conjunto mediante un único `docker-compose.yml` ubicado en la raíz del repositorio.

La autenticación es centralizada en el microservicio de Usuarios, que genera un token JWT al hacer login. Los demás microservicios validan ese mismo token de forma autónoma, usando la misma clave secreta compartida.

---

## Arquitectura de Microservicios

```
┌─────────────────────────────────────────────────────────────┐
│                    Frontend (Angular)                       │
│                    localhost:4200                           │
└──────┬────────────────┬──────────────────┬──────────────────┘
       │                │                  │
       ▼                ▼                  ▼
┌─────────────┐ ┌─────────────────┐ ┌─────────────────────┐
│  Usuarios   │ │  Laboratorios   │ │     Resultados      │
│  :8080      │ │  :8081          │ │     :8082           │
│  (Auth +    │ │                 │ │                     │
│   CRUD)     │ │  CRUD           │ │  CRUD + filtro      │
└──────┬──────┘ └────────┬────────┘ └──────────┬──────────┘
       │                  │                     │
       └──────────────────┴─────────────────────┘
                          │
                          ▼
              ┌───────────────────────┐
              │   Oracle Autonomous   │
              │   Database (Cloud)    │
              └───────────────────────┘
```

---

## Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje de desarrollo |
| Spring Boot | 3.5.7 (usuario) / 4.0.0 (lab, resultados) | Framework principal |
| Spring Security | — | Seguridad y autenticación |
| Spring Data JPA | — | Acceso a datos ORM |
| Spring HATEOAS | — | Hipermedia en respuestas REST |
| Spring Validation | — | Validación de modelos de entrada |
| JJWT (io.jsonwebtoken) | 0.11.5 | Generación y validación de tokens JWT |
| Lombok | 1.18.36 | Reducción de boilerplate |
| Oracle JDBC (ojdbc11) | — | Driver de base de datos Oracle |
| Oracle PKI / OSDT | 21.13.0.0 | Conexión segura vía Wallet a Oracle Cloud |
| SpringDoc OpenAPI | 2.3.0 | Documentación Swagger UI |
| BCrypt | — | Encriptación de contraseñas |
| Docker + eclipse-temurin:21 | — | Contenerización |

---

## Estructura del Repositorio

```
BackEnd_laboratorio_clinico-main/
├── docker-compose.yml              ← Orquesta los 3 microservicios
│
├── usuario/                        ← MS de Usuarios y Autenticación
│   ├── Dockerfile
│   ├── WALLET/                     ← Wallet de Oracle Cloud (conexión segura)
│   ├── pom.xml
│   └── src/main/java/duoc/usuarios/
│       ├── config/
│       │   ├── DataInitializer.java        ← Crea usuario ADMIN al iniciar
│       │   ├── JwtAuthenticationFilter.java
│       │   └── SecurityConfig.java
│       ├── controller/
│       │   ├── AuthController.java         ← POST /api/auth/login
│       │   ├── UsuarioController.java      ← CRUD /api/usuarios
│       │   └── GlobalExceptionHandler.java
│       ├── entity/   (Usuario, Rol, Laboratorio)
│       ├── model/    (UsuarioModel – DTO de entrada)
│       ├── repository/
│       ├── service/  (UsuarioService, UsuarioDetailsService)
│       └── utils/    (JwtUtils)
│
├── laboratorio/                    ← MS de Laboratorios
│   ├── Dockerfile
│   ├── WALLET/
│   ├── pom.xml
│   └── src/main/java/duoc/
│       ├── config/   (JwtAuthenticationFilter, SecurityConfig)
│       ├── controller/ (LaboratorioController – CRUD /api/laboratorios)
│       ├── entity/   (Laboratorio, Convenio)
│       ├── model/    (LaboratorioModel)
│       ├── repository/
│       ├── service/  (LaboratorioService)
│       └── utils/    (JwtUtils)
│
└── microservicio-resultados/       ← MS de Resultados de Exámenes
    ├── Dockerfile
    ├── WALLET/
    ├── pom.xml
    └── src/main/java/duoc/resultados/
        ├── config/   (JwtAuthenticationFilter, SecurityConfig)
        ├── controller/ (ResultadoController – CRUD /api/resultados)
        ├── entity/   (Resultado, TipoExamen, TipoParametro, UnidadMedida)
        ├── model/    (ResultadoModel)
        ├── repository/
        ├── service/  (ResultadoService)
        └── utils/    (JwtUtils)
```

---

## Microservicio: Usuarios

**Puerto:** `8080`

Gestiona usuarios del sistema e implementa la autenticación centralizada.

### Endpoints

| Método | Ruta | Descripción | Auth requerida |
|---|---|---|---|
| `POST` | `/api/auth/login` | Inicia sesión y retorna token JWT | No |
| `GET` | `/api/usuarios` | Lista todos los usuarios | Sí |
| `GET` | `/api/usuarios/{id}` | Obtiene un usuario por ID | Sí |
| `POST` | `/api/usuarios` | Crea un nuevo usuario | Sí |
| `PUT` | `/api/usuarios/{id}` | Actualiza un usuario existente | Sí |
| `DELETE` | `/api/usuarios/{id}` | Elimina un usuario | Sí |

### Modelo de entrada – `UsuarioModel`

```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "password": "secreto123",
  "rolId": 1,
  "laboratorioId": 5
}
```

> En modo edición, `password` es opcional: si se omite, se conserva la contraseña existente.

### Entidad `Usuario`

Mapeada a la tabla `usuarios`. Tiene relaciones `@ManyToOne` con `Rol` (tabla `roles`) y `Laboratorio` (tabla `laboratorios`). Las contraseñas se almacenan encriptadas con BCrypt.

---

## Microservicio: Laboratorios

**Puerto:** `8081`

Gestiona los laboratorios clínicos y sus convenios asociados.

### Endpoints

| Método | Ruta | Descripción | Auth requerida |
|---|---|---|---|
| `GET` | `/api/laboratorios` | Lista todos los laboratorios | Sí |
| `GET` | `/api/laboratorios/{id}` | Obtiene un laboratorio por ID | Sí |
| `POST` | `/api/laboratorios` | Crea un nuevo laboratorio | Sí |
| `PUT` | `/api/laboratorios/{id}` | Actualiza un laboratorio existente | Sí |
| `DELETE` | `/api/laboratorios/{id}` | Elimina un laboratorio | Sí |

### Modelo de entrada – `LaboratorioModel`

```json
{
  "nombre": "Laboratorio Central",
  "telefono": "+56912345678",
  "webUrl": "www.labcentral.cl",
  "email": "contacto@labcentral.cl",
  "convenioId": 2
}
```

### Entidades

- `Laboratorio` — tabla `laboratorios`. Campos: `nombre`, `telefono`, `webUrl`, `email`, relación `@ManyToOne` con `Convenio`.
- `Convenio` — tabla `convenios`. Entidad de referencia para el tipo de convenio del laboratorio.

---

## Microservicio: Resultados

**Puerto:** `8082`

Gestiona los resultados de exámenes clínicos, incluyendo los parámetros de referencia, el tipo de examen y los datos del paciente y laboratorio asociados.

### Endpoints

| Método | Ruta | Descripción | Auth requerida |
|---|---|---|---|
| `GET` | `/api/resultados` | Lista todos los resultados | Sí |
| `GET` | `/api/resultados/{id}` | Obtiene un resultado por ID | Sí |
| `GET` | `/api/resultados/paciente/{usuarioId}` | Lista resultados por paciente | Sí |
| `POST` | `/api/resultados` | Registra un nuevo resultado | Sí |
| `PUT` | `/api/resultados/{id}` | Actualiza un resultado existente | Sí |
| `DELETE` | `/api/resultados/{id}` | Elimina un resultado | Sí |

### Modelo de entrada – `ResultadoModel`

```json
{
  "valorResultado": 5.2,
  "valorRefMin": 3.5,
  "valorRefMax": 6.0,
  "observacion": "Dentro de rango normal",
  "fechaExamen": "2025-12-20",
  "laboratorioId": 1,
  "usuarioId": 3,
  "tipoExamenId": 1,
  "tipoParametroId": 2,
  "unidadMedidaId": 1
}
```

### Entidades

- `Resultado` — tabla `resultados`. Contiene los valores numéricos del examen, fecha, y relaciones con `TipoExamen`, `TipoParametro` y `UnidadMedida`.
- `TipoExamen`, `TipoParametro`, `UnidadMedida` — tablas de referencia para catalogar los exámenes.

---

## Seguridad y Autenticación JWT

El flujo de autenticación es el siguiente:

1. El cliente realiza `POST /api/auth/login` con `email` y `password`.
2. El microservicio de Usuarios valida las credenciales mediante Spring Security.
3. Si son correctas, genera un token JWT firmado con HMAC-SHA256 y lo retorna.
4. El cliente adjunta el token en el header de cada petición protegida: `Authorization: Bearer <token>`.
5. Cada microservicio tiene su propio `JwtAuthenticationFilter` que intercepta las peticiones y valida el token de forma autónoma, usando la misma clave secreta.

**Duración del token:** 24 horas (`86400000 ms`).

> ⚠️ **Seguridad:** La clave secreta JWT (`SECRET_KEY` en `JwtUtils.java`) está actualmente hardcodeada en el código fuente. En producción, debe ser configurada como variable de entorno o mediante un sistema de secrets (Vault, Kubernetes Secrets, etc.).

### Configuración CORS

El `SecurityConfig` del microservicio de Usuarios permite peticiones desde los siguientes orígenes:
- `http://localhost:4200` (frontend Angular)
- `http://localhost:8080`, `8081`, `8082` (inter-servicios)

---

## Base de Datos Oracle Cloud

Los tres microservicios se conectan a una instancia de **Oracle Autonomous Database** alojada en **Oracle Cloud Infrastructure (OCI)** en la región `sa-santiago-1`.

La conexión se realiza de forma segura mediante un **Oracle Wallet**, que contiene los certificados SSL y el archivo `tnsnames.ora` con la dirección del servicio (`laboratoriofs3_high`).

Cada microservicio tiene su propia carpeta `WALLET/` con las credenciales de conexión. En Docker, esta carpeta se monta como volumen en `/app/wallet` dentro del contenedor, y la variable de entorno `TNS_ADMIN` apunta a esa ruta.

> ⚠️ **El Wallet expira el 2 de noviembre de 2030.** Descarga uno nuevo desde la consola de OCI antes de esa fecha para evitar interrupciones.

> ⚠️ Las credenciales de la base de datos (`SPRING_DATASOURCE_PASSWORD`) están visibles en el `docker-compose.yml`. En producción, utiliza un archivo `.env` o un gestor de secretos y nunca subas credenciales al repositorio.

---

## Requisitos Previos

- [Java 21](https://adoptium.net/) (JDK)
- [Maven 3.9+](https://maven.apache.org/)
- [Docker](https://www.docker.com/) y [Docker Compose](https://docs.docker.com/compose/) (para despliegue contenerizado)
- Acceso a la instancia Oracle Cloud (Wallet ya incluido en el repositorio)

---

## Ejecución Local (sin Docker)

Cada microservicio se ejecuta de forma independiente. Es necesario compilar y levantar cada uno por separado.

```bash
# Ejemplo para el microservicio de Usuarios
cd usuario
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

```bash
# Microservicio de Laboratorios (puerto 8081)
cd laboratorio
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

```bash
# Microservicio de Resultados (puerto 8082)
cd microservicio-resultados
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

> Antes de ejecutar, asegúrate de que la ruta al Wallet esté correcta en `application.properties` (`TNS_ADMIN=WALLET` apunta a la carpeta `WALLET/` local).

---

## Despliegue con Docker Compose

El `docker-compose.yml` en la raíz levanta los tres microservicios a la vez. **Antes de ejecutarlo**, cada microservicio debe estar compilado (JAR generado en su carpeta `target/`).

```bash
# Paso 1: Compilar los tres microservicios
cd usuario && ./mvnw clean package -DskipTests && cd ..
cd laboratorio && ./mvnw clean package -DskipTests && cd ..
cd microservicio-resultados && ./mvnw clean package -DskipTests && cd ..

# Paso 2: Construir y levantar los contenedores
docker-compose up --build
```

| Servicio | Contenedor | Puerto |
|---|---|---|
| Microservicio Usuarios | `api-usuarios` | `8080` |
| Microservicio Laboratorios | `api-laboratorios` | `8081` |
| Microservicio Resultados | `api-resultados` | `8082` |

Para detener los servicios:

```bash
docker-compose down
```

---

## Variables de Entorno

Las siguientes variables se configuran por servicio en el `docker-compose.yml`. También pueden sobreescribirse para otros entornos:

| Variable | Descripción | Ejemplo |
|---|---|---|
| `SERVER_PORT` | Puerto en que corre el servicio | `8080` |
| `SPRING_DATASOURCE_URL` | URL de conexión Oracle con TNS | `jdbc:oracle:thin:@laboratoriofs3_high?TNS_ADMIN=/app/wallet` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de base de datos | `ADMIN` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de base de datos | `***` |

> Para entornos productivos, extrae estas variables a un archivo `.env` y agrégalo a `.gitignore`.

---

## Usuario Administrador por Defecto

El microservicio de Usuarios incluye un `DataInitializer` que, al iniciar por primera vez, crea automáticamente un rol y un usuario administrador si no existen en la base de datos:

| Campo | Valor |
|---|---|
| Email | `admin@duoc.cl` |
| Contraseña | `123456` |
| Rol | `ADMIN` |

> Cambia esta contraseña inmediatamente en un entorno de producción.

---

## Documentación de la API (Swagger)

Los microservicios de **Usuarios** y **Resultados** incluyen SpringDoc OpenAPI. La interfaz Swagger UI está disponible una vez levantado cada servicio:

- Usuarios: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Resultados: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

La especificación OpenAPI en JSON está en `/v3/api-docs`.

> El microservicio de Laboratorios no incluye SpringDoc en su `pom.xml` actual.

---

## Pruebas

Cada microservicio incluye una clase de test básica generada por Spring Initializr. Para ejecutarlas:

```bash
# Dentro de la carpeta de cada microservicio
./mvnw test
```

> Las pruebas de contexto de Spring requieren conexión a la base de datos Oracle. Si no hay conectividad, los tests de arranque fallarán. Puedes usar `@SpringBootTest` con un perfil de test que apunte a una BD en memoria (H2) para pruebas unitarias aisladas.
