## ADDED Requirements
### Requirement: User Registration Protocol
SDK MUST provide protocol definitions for user registration including request and response structures.

#### Scenario: Register request protocol
- **GIVEN** a UserService definition in user.proto
- **WHEN** a register method is added
- **THEN** it MUST accept RegisterRequest with username and password fields
- **AND** it MUST return RegisterResponse with user_id and user_info fields

### Requirement: User Registration Gateway API
Gateway MUST expose HTTP API endpoint for user registration that validates input and returns standardized result.

#### Scenario: Successful registration
- **WHEN** POST /user/register is called with valid username and password
- **THEN** it MUST return 200 status with user information
- **AND** it MUST call User service via gRPC
- **AND** it MUST use Sa-Token to manage user session

#### Scenario: Registration with invalid input
- **WHEN** POST /user/register is called with empty username or password
- **THEN** it MUST return 400 Bad Request
- **AND** it MUST provide clear error message

#### Scenario: Registration with duplicate username
- **WHEN** POST /user/register is called with an existing username
- **THEN** it MUST return 409 Conflict
- **AND** it MUST indicate username already exists

### Requirement: User Registration Service Implementation
User service MUST implement the registration logic including parameter validation, user creation, and data persistence.

#### Scenario: Create new user
- **WHEN** register RPC is called with valid credentials
- **THEN** it MUST validate username uniqueness
- **AND** it MUST hash the password
- **AND** it MUST persist user record to database
- **AND** it MUST return the created user information

#### Scenario: Reject duplicate registration
- **WHEN** register RPC is called with an existing username
- **THEN** it MUST NOT create duplicate user
- **AND** it MUST return error indicating username conflict
