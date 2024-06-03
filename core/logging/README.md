# Logging Module

This module provides a simple and efficient way to log messages in your application. It uses the `Kermit` library internally, but does not expose it, providing a clean and easy-to-use API for logging.

## Features

- Verbose, debug, info, warn, error, and assert logging levels.
- Automatic initialization of the logger at application startup.
- Ability to disable logging in release builds for efficiency.

## Usage

In your module `build.gradle.kts` file, add the following dependency:

```kotlin
implementation(projects.core.logging.api)
```

To use the logging functions, simply use the `appLogger` object:

```kotlin
appLogger.debug("MyTag", "This is a debug message")
```

You can also provide a `Throwable` to be logged:

```kotlin
appLogger.error("MyTag", "An error occurred", throwable)
```

## Disabling Logging in Release Builds
Logging is automatically disabled in release builds. This is done in the `AppLoggerInitializer` class, which checks if the build is a debug build and disables logging if it's not.

## Testing
Unit tests for the InternalLogger class are provided. These tests use the MockK library to verify that the correct methods of the Logger class from Kermit are called.

## Dependencies
This module depends on the following libraries:
- Kermit
- MockK (for testing)
