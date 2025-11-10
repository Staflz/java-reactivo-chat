# Reactive Chat Demo with WebSocket

A modern, reactive chat application built with Spring Boot 3.5.7, featuring real-time WebSocket communication and a clean, educational architecture.

## 🚀 Features

- **Reactive Architecture**: Built with Spring WebFlux and Project Reactor for non-blocking, reactive communication
- **Real-time WebSocket Communication**: Instant message delivery and broadcasting
- **Server-Sent Events (SSE)**: Alternative real-time communication method
- **Clean Architecture**: Well-structured with proper separation of concerns
- **Educational Design**: Clear, documented code perfect for learning reactive programming
- **Production Ready**: Configured for cloud deployment on Render

## 🏗️ Architecture

### Project Structure
```
src/
├── main/java/com/example/chat/
│   ├── ChatApplication.java          # Main Spring Boot application
│   ├── controller/                   # REST controllers
│   │   ├── ChatController.java       # API endpoints
│   │   └── ChatInterfaceController.java # Web interface
│   ├── service/                      # Business logic
│   │   ├── ChatService.java
│   │   └── ChatServiceImpl.java      # Reactive implementation
│   ├── model/                        # Domain models
│   │   └── Message.java
│   ├── dto/                          # Data Transfer Objects
│   │   ├── MessageRequest.java
│   │   └── MessageResponse.java
│   ├── handler/                      # WebSocket handlers
│   │   └── ChatWebSocketHandler.java # WebSocket logic
│   ├── config/                       # Configuration
│   │   ├── ChatConstants.java
│   │   ├── ChatProperties.java
│   │   └── WebSocketRouter.java
│   ├── repository/                   # Data access
│   │   ├── MessageRepository.java
│   │   └── InMemoryMessageRepository.java
│   ├── mapper/                       # Object mapping
│   │   └── MessageMapper.java
│   └── exception/                    # Error handling
│       ├── ErrorResponse.java
│       └── GlobalExceptionHandler.java
└── resources/
    ├── static/                       # Static web assets
    │   └── chat.html                 # WebSocket chat interface
    └── application.properties        # Application configuration
```

### Key Components

1. **ChatService**: Core business logic using reactive Sinks for message broadcasting
2. **WebSocketHandler**: Handles real-time WebSocket connections
3. **Message Flow**: Client → WebSocket → Service → All Clients
4. **Reactive Streams**: Uses Flux and Sinks for non-blocking communication

## 🛠️ Technologies

- **Spring Boot 3.5.7** with Java 21
- **Spring WebFlux** for reactive web applications
- **Project Reactor** for reactive programming
- **MongoDB Reactive** (optional, currently using in-memory storage)
- **Lombok** for reduced boilerplate code
- **Jackson** for JSON processing
- **Maven** for dependency management

## 🏃‍♂️ Running Locally

### Prerequisites
- Java 21 or later
- Maven 3.6+

### Steps
1. **Clone and build**:
   ```bash
   git clone <repository-url>
   cd demo
   mvn clean package
   ```

2. **Run the application**:
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

3. **Access the application**:
   - Web Interface: http://localhost:8080/chat
   - API Base: http://localhost:8080/chat
   - WebSocket: ws://localhost:8080/ws/chat

### Testing the API

#### Send a message:
```bash
curl -X POST http://localhost:8080/chat/send \
  -H "Content-Type: application/json" \
  -d '{"sender":"User1","content":"Hello, reactive world!"}'
```

#### Stream messages (SSE):
```bash
curl http://localhost:8080/chat/stream
```

## 🌐 Deploying to Render

### Prerequisites
- GitHub account with your code
- Render account (free tier available)

### Step-by-Step Deployment

1. **Push to GitHub**:
   ```bash
   git add .
   git commit -m "Add WebSocket reactive chat"
   git push origin main
   ```

2. **Create Render App**:
   - Go to [Render.com](https://render.com)
   - Click "New +" → "Web Service"
   - Connect your GitHub repository

3. **Configure Build Settings**:
   - **Runtime**: Java 21
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -Dserver.port=$PORT -jar target/demo-0.0.1-SNAPSHOT.jar`

4. **Environment Variables** (Optional):
   - `PORT`: Leave empty (Render sets this)
   - `SPRING_PROFILES_ACTIVE`: `production`

5. **Deploy**:
   - Click "Create Web Service"
   - Wait for build and deployment (2-3 minutes)
   - Your app will be available at: `https://your-app-name.onrender.com`

### Verify Deployment

- Visit `https://your-app-name.onrender.com/chat` for the web interface
- Test the API endpoints:
  - Health check: `https://your-app-name.onrender.com/chat/health`
  - Send message: `POST /chat/send`
  - Stream messages: `GET /chat/stream`

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/chat/health` | Health check |
| GET | `/chat/stream` | Server-Sent Events stream |
| POST | `/chat/send` | Send a message |
| GET | `/chat` | Web interface |
| WS | `/ws/chat` | WebSocket endpoint |

## 🧪 Testing the WebSocket

### Using Browser Console
1. Open `http://localhost:8080/chat` or your Render URL
2. Open browser console (F12)
3. The chat will auto-connect to WebSocket
4. Type messages and see real-time delivery

### Using WebSocket Client
```javascript
const ws = new WebSocket('ws://localhost:8080/ws/chat');

ws.onopen = () => {
    console.log('Connected');
    ws.send(JSON.stringify({
        sender: 'TestUser',
        content: 'Hello WebSocket!'
    }));
};

ws.onmessage = (event) => {
    console.log('Received:', JSON.parse(event.data));
};
```

## 🔧 Configuration

### Application Properties
```properties
spring.application.name=reactive-chat-demo
server.port=${PORT:8080}
spring.main.web-application-type=reactive

# Chat configuration
chat.max-buffer-size=1000

# WebSocket path
spring.webflux.websocket.path=/ws/chat

# Logging
logging.level.com.example.demo=INFO
logging.level.reactor.netty=INFO
```

### Chat Properties
- `chat.max-buffer-size`: Maximum messages to buffer (default: 1000)

## 🎯 Learning Objectives

This project demonstrates:

1. **Reactive Programming**:
   - Using Flux and Sinks
   - Non-blocking I/O operations
   - Event-driven architecture

2. **WebSocket Implementation**:
   - Real-time bidirectional communication
   - Connection management
   - Message serialization

3. **Spring Boot Best Practices**:
   - Clean architecture
   - Proper configuration management
   - Error handling

4. **Cloud Deployment**:
   - Render deployment configuration
   - Environment variable management
   - Production-ready setup

## 🤝 Contributing

This is an educational project, but contributions are welcome:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📝 License

This project is for educational purposes. Feel free to use and modify as needed.

## 🆘 Troubleshooting

### Common Issues

1. **WebSocket Connection Failed**:
   - Check if the WebSocket endpoint is accessible
   - Verify firewall/proxy settings
   - Ensure WebSocket support is enabled

2. **Messages Not Broadcasting**:
   - Check the reactive sink configuration
   - Verify service injection
   - Review logging output

3. **Build Failures**:
   - Ensure Java 21 is available
   - Check Maven version compatibility
   - Verify all dependencies are resolved

### Debug Mode
Enable debug logging in `application.properties`:
```properties
logging.level.com.example.chat=DEBUG
logging.level.reactor.netty=DEBUG
```

---

**Happy Coding! 🎉**