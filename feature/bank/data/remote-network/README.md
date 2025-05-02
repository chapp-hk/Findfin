# `:feature:bank:data:remote-network`

This module provides the remote network implementation for the Bank feature in the application. It uses **Ktor** as the HTTP client to interact with the remote API and fetch bank-related data.

## Features

- **BankApi**: Defines the API contract for bank-related operations.
- **BankApiImpl**: Implements the `BankApi` interface using **Ktor**.
- **BankRemoteDataSource**: Provides methods to interact with the `BankApi` and process API responses.

## Components

### 1. `BankApi`
Defines the contract for API operations, including:
- `getLocations`: Fetches a list of bank locations based on query parameters.

### 2. `BankApiImpl`
Implements the `BankApi` interface using **Ktor**. Key responsibilities include:
- Configuring the HTTP client using `HttpClientFactory`.
- Making API requests to fetch bank data.
- Mapping API responses to the appropriate models.

### 3. `BankRemoteDataSource`
- Acts as a bridge between the `BankApi` and the repository layer.
- Handles API calls and processes responses, including error handling.
- Uses `BankApiImpl` to fetch data from the server.

## Usage

### Dependency
Include the module in your Gradle dependencies:
```gradle
implementation project(":feature:bank:data:remote-network")
```

### Example
To fetch bank data from the server:
```kotlin
val bankRemoteDataSource: BankRemoteDataSource = // Injected via Hilt

// Fetch bank locations
val result = bankRemoteDataSource.getLocations(
    path = TypePath("banks"),
    language = "en",
    pageSize = 10,
    offset = 0
)

when (result) {
    is BankRemoteResult.Success -> {
        val banks = result.data
        // Handle success
    }
    is BankRemoteResult.Error -> {
        // Handle error
    }
}
```

## Requirements

- **Kotlin**
- **Ktor** for network communication
- **Hilt** for dependency injection

## License

This module is part of the project and follows the project's licensing terms.
