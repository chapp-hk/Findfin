# `:feature:bank:data:local-database`

This module provides the local database implementation for the Bank feature in the application. It uses **Room** as the persistence library to manage the local database and perform CRUD operations.

## Features

- **BankEntity**: Defines the schema for the `bank` table.
- **BankDao**: Provides methods to interact with the `bank` table, such as inserting, querying, and retrieving data.
- **BankDatabase**: Configures the Room database and integrates with Hilt for dependency injection.

## Components

### 1. `BankEntity`
Represents the data model for the `bank` table. Key fields include:
- `id`: Primary key (auto-generated).
- `language`: Language of the bank data.
- `type`: Type of the bank.
- `district`: District where the bank is located.
- `bankName`: Name of the bank.
- `latitude` and `longitude`: Geographical coordinates of the bank.

### 2. `BankDao`
Defines the following database operations:
- `insertAll`: Inserts or replaces a list of `BankEntity` records.
- `getDistinctBankNames`: Fetches distinct bank names for a given language.
- `getDistricts`: Fetches distinct districts for a given language.
- `getBanksWithQuery`: Fetches banks based on a search query.

### 3. `BankDatabase`
- Configures the Room database with the `BankEntity` table.
- Exposes the `BankDao` for database operations.
- Uses `@HiltWrapRoomModule` and `@HiltWrapRoomDao` annotations for Hilt integration.

## Usage

### Dependency
Include the module in your Gradle dependencies:
```gradle
implementation project(":feature:bank:data:local-database")
```

### Example
To interact with the database:
```kotlin
val bankDao = bankDatabase.bankDao

// Insert banks
bankDao.insertAll(listOf(bankEntity1, bankEntity2))

// Query banks within bounds
val banks = bankDao.getBanksWithinBound(
    language = "en",
    minLat = 10.0,
    maxLat = 20.0,
    minLon = 30.0,
    maxLon = 40.0
)
```

## Requirements

- **Kotlin**
- **Room** (AndroidX)
- **Hilt** for dependency injection

## License

This module is part of the project and follows the project's licensing terms.
