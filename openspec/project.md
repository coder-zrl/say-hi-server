# Project Context

## Purpose
Say Hi Server is a Spring Boot-based instant messaging (IM) system backend service. It provides real-time communication capabilities including user management, chat sessions, group messaging, message processing, and user interactions through a microservices architecture. The system serves as the backend infrastructure for a chat application.

**Architecture Pattern**: Microservices with API Gateway pattern
- All external interfaces are provided through the `say-hi-gateway` module
- Business modules expose gRPC interfaces internally
- Gateway calls these internal RPC interfaces to fulfill external requests

## Tech Stack

### Core Technologies
- **Java**: Version 21 (Latest LTS)
- **Spring Boot**: Version 3.3.2
- **Build Tool**: Maven (multi-module project)

### Communication & Protocols
- **gRPC**: For inter-service communication (microservices)
- **Protocol Buffers**: Data serialization (v3.23.4)
- **WebSocket**: For real-time long connections (maintained by gateway)
- **HTTP/REST**: External API interface (through gateway)

### Data & Storage
- **MySQL**: Primary database
- **MyBatis-Plus**: ORM framework
- **Redis**: Caching and session storage

### Security & Authentication
- **Sa-Token**: Permission authentication framework
  - sa-token-spring-boot3-starter (v1.44.0)
  - sa-token-redis-template (v1.44.0)
  - sa-token-fastjson (v1.44.0)

### Development & Documentation
- **Knife4j**: API documentation (v4.4.0)
- **Lombok**: Boilerplate code reduction (v1.18.38)
- **Hutool**: Utility library (v5.8.16)

### Cloud & Discovery
- **Nacos**: Service discovery and configuration
  - spring-cloud-alibaba-nacos-discovery (v2023.0.3.3)
  - spring-cloud-alibaba-nacos-config (v2023.0.3.3)

## Project Structure

### Module Organization
```
say-hi-server (parent pom)
├── say-hi-application      # Application bootstrap and service coordination
├── say-hi-common           # Shared utilities and configurations
├── say-hi-gateway          # API Gateway (HTTP/WebSocket entry point)
├── say-hi-sdk              # Protocol Buffers definitions and gRPC interfaces
├── say-hi-user             # User management service
├── say-hi-im-chat          # Chat session management service
├── say-hi-im-group         # Group chat service
├── say-hi-im-message       # Message handling and storage service
└── say-hi-interaction      # User interaction service (likes, etc.)
```

### Per-Module Structure
Each business module follows this pattern:
```
src/main/java/com.bird....[module]
├── controller  # REST controllers (mainly for testing)
├── entity      # Database entity classes
├── mapper      # MyBatis mapper interfaces
├── rpc         # gRPC service implementations
└── service     # Business logic services

src/main/resources
└── mapper      # XML-based SQL implementations
```

## Project Conventions

### Code Style
- **Java Version**: Must use Java 21 features where appropriate
- **Documentation**: All new Java classes and methods MUST have clear header comments for AI assistant understanding
- **Packages**: Follow reverse domain naming: `com.bird.im.[module]`
- **Logging**: Use appropriate logging levels; business logic should be in Service layer, not RPC layer

### Architecture Patterns

#### Gateway Pattern (Mandatory)
- **External Interface**: Only `say-hi-gateway` module can expose HTTP APIs
- **Internal Communication**: Gateway calls gRPC interfaces of business modules
- **WebSocket**: Long connections maintained only in gateway module
- **Data Flow**: Gateway entities defined as Java objects; internal services use Protocol Buffers

#### Layer Separation (Strict)
1. **RPC Layer** (`rpc/`): Thin wrapper implementing gRPC interfaces
   - Use `GrpcTemplate.execute()` for all RPC implementations
   - Parameter validation only
   - Delegate to Service layer
   - Template pattern required (see 开发规范.md)

2. **Service Layer** (`service/`): Business logic
   - Contains actual business operations
   - Orchestrates mapper calls
   - Independent of RPC framework

3. **Mapper Layer** (`mapper/`): Data access
   - Direct database operations
   - XML-based SQL in `resources/mapper/`

#### RPC Implementation Template (Required)
```java
@Override
public void methodName(Request request, StreamObserver<Response> responseObserver) {
    String invokeName = "ServiceName.methodName";

    // Parameter validation
    Consumer<Request> checkParams = req -> {
        checkArgument(req.getField() > 0, "invalid field");
    };

    // Business logic delegation
    ProcessFunc<Request, Response> processFunc = req -> {
        return service.method(req);
    };

    // Execute with template
    GrpcTemplate.execute(invokeName, request, checkParams, processFunc,
        getResponseProcess(Response.class), responseObserver);
}
```

### Data Model Consistency
- **Gateway**: Uses Java objects directly
- **Internal Services**: Use Protocol Buffers
- **Synchronization**: Data models must be consistent between gateway and services
- **Definition Location**: Proto files in `say-hi-sdk` module

### Development Workflow
When adding a new RPC interface:
1. Read/define proto file in `say-hi-sdk` module
2. Consider common business scenarios and table structure
3. Implement RPC interface in service module's `rpc/` folder
4. Implement Service and Mapper layers
5. Recompile SDK module for IDE recognition

### Testing Strategy
- Controller layer mainly for testing purposes
- RPC layer should be tested via integration tests
- Service layer contains unit testable business logic
- Use Spring Boot test framework

### Git Workflow
- **Branching**: Follow standard feature branching
- **Commits**: Conventional commit messages
- **Modules**: Changes should be isolated to specific modules when possible
- **Dependencies**: Be mindful of module interdependencies

## Domain Context

### IM System Concepts
- **Users**: Managed by `say-hi-user` service
- **Chat Sessions**: One-on-one conversations (managed by `say-hi-im-chat`)
- **Group Chats**: Multi-user conversations (managed by `say-hi-im-group`)
- **Messages**: Individual messages with types (managed by `say-hi-im-message`)
- **Interactions**: User interactions like likes, reactions (managed by `say-hi-interaction`)

### Communication Patterns
- **Synchronous**: HTTP RESTful API (external) + gRPC (internal)
- **Asynchronous**: WebSocket for real-time message push
- **Protocol Buffers**: Primary serialization format for service communication

### Service Responsibilities
- **Gateway**: Request routing, auth validation, API documentation, WebSocket management
- **User**: User CRUD, authentication, profile management
- **Chat**: Session lifecycle management, participant management
- **Group**: Group creation, membership, permissions
- **Message**: Message persistence, retrieval, delivery status
- **Interaction**: Likes, reactions, read receipts
- **SDK**: Shared proto definitions and gRPC service interfaces

## Important Constraints

1. **No Direct External Access**: Business modules cannot expose HTTP interfaces directly
2. **RPC Template Compliance**: All RPC implementations MUST follow the GrpcTemplate pattern
3. **Layer Separation**: Business logic MUST be in Service layer, not RPC layer
4. **Data Model Consistency**: Gateway Java objects and protobuf messages must match semantically
5. **Comment Requirements**: New code MUST have clear header comments for AI understanding
6. **SDK Recompilation**: After modifying proto files, SDK module must be recompiled

## External Dependencies

### Infrastructure Services
- **MySQL**: Primary database storage
- **Redis**: Caching and session management
- **Nacos**: Service discovery and configuration management

### Third-Party Services
- **Sa-Token**: Authentication and authorization
- **Knife4j**: API documentation generation

### Communication Protocols
- **gRPC**: Service-to-service RPC calls
- **WebSocket**: Real-time bidirectional communication
- **HTTP**: RESTful API for external clients

## Development Standards Reference
For detailed development guidelines, refer to: `@/docs/开发规范.md`
