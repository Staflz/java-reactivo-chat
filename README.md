# Chat Application - Reactive Demo

Una aplicación de chat reactiva construida con Spring Boot WebFlux que demuestra el uso de Server-Sent Events (SSE) para comunicación en tiempo real.

## Arquitectura

La aplicación sigue una arquitectura limpia con capas bien definidas:

- **Controller**: Maneja las solicitudes HTTP y respuestas
- **Service**: Contiene la lógica de negocio y gestión de mensajes reactivos
- **Repository**: Maneja el almacenamiento de mensajes (in-memory)
- **Model/DTO**: Representaciones de datos
- **Mapper**: Conversión entre entidades y DTOs
- **Config**: Configuraciones y constantes

## Tecnologías

- **Spring Boot 3.5.7** con **Java 21**
- **Spring WebFlux** para reactividad
- **Project Reactor** para streams reactivos
- **Lombok** para reducción de boilerplate
- **Jakarta Validation** para validación de entrada

## Endpoints

### POST /chat/send
Envía un nuevo mensaje al chat.

**Request Body:**
```json
{
  "sender": "usuario123",
  "content": "Hola a todos!"
}
```

**Response:** 200 OK (sin cuerpo)

### GET /chat/stream
Obtiene un stream de mensajes en tiempo real usando Server-Sent Events.

**Response:** Stream de eventos SSE con mensajes en formato JSON

## Configuración

La aplicación se configura a través de `application.properties`:

```properties
spring.application.name=demo
server.port=8080
spring.main.web-application-type=reactive

# Chat service configuration
chat.max-buffer-size=1000

# Logging configuration
logging.level.com.example.chat=INFO
logging.level.reactor.netty=INFO
```

## Demostración de Reactividad con Postman

### Prerrequisitos
1. Iniciar la aplicación: `mvnw.cmd spring-boot:run`
2. La aplicación estará disponible en `http://localhost:8080`

### Pasos para demostrar la reactividad

#### 1. Configurar el Stream SSE
- **Método:** GET
- **URL:** `http://localhost:8080/chat/stream`
- **Headers:**
  - `Accept: text/event-stream`
  - `Cache-Control: no-cache`

En Postman:
1. Seleccionar método GET
2. Ingresar URL: `http://localhost:8080/chat/stream`
3. Ir a la pestaña "Headers"
4. Agregar header: `Accept` con valor `text/event-stream`
5. Agregar header: `Cache-Control` con valor `no-cache`
6. Hacer clic en "Send"

**Nota:** El stream se mantendrá abierto. Los mensajes aparecerán en tiempo real.

#### 2. Enviar Mensajes (en otra instancia de Postman o terminal)

Mientras el stream SSE esté abierto en una pestaña/ventana, abre otra para enviar mensajes:

- **Método:** POST
- **URL:** `http://localhost:8080/chat/send`
- **Headers:**
  - `Content-Type: application/json`
- **Body (raw JSON):**
```json
{
  "sender": "Usuario1",
  "content": "¡Hola desde Postman!"
}
```

#### 3. Observar la Reactividad

Al enviar mensajes desde la segunda instancia, deberías ver inmediatamente en la primera instancia (stream SSE) cómo aparecen los nuevos mensajes en tiempo real.

**Ejemplo de respuesta SSE:**
```
data: {"sender":"Usuario1","content":"¡Hola desde Postman!","timestamp":"2025-11-03T20:41:21.216Z"}

data: {"sender":"Usuario2","content":"¡Hola también!","timestamp":"2025-11-03T20:41:22.123Z"}
```

### Validación de Errores

Si envías un mensaje con campos vacíos:

**Request:**
```json
{
  "sender": "",
  "content": ""
}
```

**Response (400 Bad Request):**
```json
{
  "status": 400,
  "message": "Validation failed",
  "details": "sender: Sender cannot be blank; content: Content cannot be blank;"
}
```

## Características Técnicas

- **Reactividad**: Usa `Sinks.Many<Message>` para multicast reactivo
- **Buffer de respaldo**: Configurable para manejar sobrecargas
- **Validación**: Validación automática de entrada con Jakarta Validation
- **Manejo de errores**: Respuestas estructuradas para errores
- **Logging**: Logging estructurado en service y controller
- **Inmutabilidad**: DTOs como records para thread-safety
- **Métricas**: Contador simple de mensajes procesados

## Desarrollo y Aprendizaje

Este proyecto es educativo y demuestra:
- Programación reactiva con Spring WebFlux
- Server-Sent Events para comunicación bidireccional
- Arquitectura limpia y buenas prácticas
- Manejo de concurrencia en aplicaciones reactivas
- Configuración tipada con Spring Boot

Para desarrollo futuro, considera:
- Migración a WebSockets para comunicación bidireccional completa
- Integración con base de datos reactiva (MongoDB)
- Autenticación y autorización
- Tests unitarios e integración