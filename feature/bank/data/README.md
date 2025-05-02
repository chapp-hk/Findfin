# `:feature:bank:data`

This module serves as the parent module for the Bank feature's data layer. It is designed to encapsulate the data-related logic by delegating responsibilities to its submodules: `:feature:bank:data:local-database`, `:feature:bank:data:remote-network`, and `:feature:bank:data:repo`.

## Features

- **Modularized Architecture**: Divides the data layer into focused submodules for better maintainability and scalability.
- **Centralized Dependency Management**: Provides a single entry point for managing dependencies across the data submodules.
- **Integration with Domain Layer**: Acts as the bridge between the domain layer and the data sources.

## Submodules

The data layer is split into three distinct modules to achieve the following benefits:

1. **Separation of Concerns**:
   Each module has a specific responsibility:
    - `:feature:bank:data:local-database`: Manages local data storage using Room.
    - `:feature:bank:data:remote-network`: Handles remote API communication using Ktor.
    - `:feature:bank:data:repo`: Combines data from local and remote sources and provides a unified interface to the domain layer.

2. **Scalability**:
   Modularization allows independent development and testing of each module. For example, changes to the remote network logic do not affect the local database module.

3. **Reusability**:
   Each module can be reused across different features or projects. For instance, the `:feature:bank:data:remote-network` module can be shared with other features requiring similar API interactions.

4. **Improved Build Times**:
   By isolating modules, only the affected module is rebuilt during development, reducing overall build times.

5. **Team Collaboration**:
   Teams can work on different modules simultaneously without conflicts, improving productivity.

### 1. `:feature:bank:data:local-database`
- Manages local data storage using Room.
- Provides database operations for bank-related data.

### 2. `:feature:bank:data:remote-network`
- Handles API communication using Ktor.
- Fetches bank-related data from the remote server.

### 3. `:feature:bank:data:repo`
- Combines data from local and remote sources.
- Exposes a unified interface to the domain layer.

## Usage

### Dependency
Include the module in your Gradle dependencies:
```gradle
implementation project(":feature:bank:data")
```

## Requirements

- **Kotlin**
- **Room** for local database operations
- **Ktor** for network communication
- **Hilt** for dependency injection

## License

This module is part of the project and follows the project's licensing terms.
