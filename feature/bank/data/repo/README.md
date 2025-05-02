# `:feature:bank:data:repo`

This module provides the repository implementation for the Bank feature in the application. It acts as a mediator between the data sources (local database and remote network) and the domain layer, ensuring a clean separation of concerns.

## Features

- **BankRepository**: Exposes methods to fetch and manage bank-related data.
- **BankRemoteDataSource**: Handles data retrieval from the remote API.
- **BankLocalDataSource**: Manages data storage and retrieval from the local database.

## Components

### 1. `BankRepository`
- Combines data from both local and remote sources.
- Provides methods to:
    - Fetch bank locations.
    - Cache data locally for offline access.
    - Handle errors and fallback mechanisms.

### 2. `BankRemoteDataSource`
- Fetches bank data from the remote API using the `:feature:bank:data:remote-network` module.
- Handles API calls and processes responses.

### 3. `BankLocalDataSource`
- Manages local database operations using the `:feature:bank:data:local-database` module.
- Provides methods to store and retrieve bank data.

## Usage

### Dependency
Include the module in your Gradle dependencies:
```gradle
implementation project(":feature:bank:data:repo")
```

### Example
To interact with the repository:
```kotlin
val bankRepository: BankRepository = // Injected via Hilt

// Fetch bank locations
val result = bankRepository.getBankLocations(
    language = "en",
    pageSize = 10,
    offset = 0
)

when (result) {
    is BankResult.Success -> {
        val banks = result.data
        // Handle success
    }
    is BankResult.Error -> {
        // Handle error
    }
}
```

## Requirements

- **Kotlin**
- **Hilt** for dependency injection
- **Room** for local database operations
- **Ktor** for network communication

## License

This module is part of the project and follows the project's licensing terms.
