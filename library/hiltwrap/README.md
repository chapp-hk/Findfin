### `:library:hiltwrap` Module

The `:library:hiltwrap` module provides annotations and processors to facilitate Hilt dependency injection with additional features for Room database integration and custom bindings.

#### Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Annotations](#annotations)

### Installation

To include the `:library:hiltwrap` module in your project, add the following dependency to your `build.gradle` file:

```gradle
dependencies {
    implementation project(':library:hiltwrap:annotation')
    ksp project(':library:hiltwrap:processor-binds')
    ksp project(':library:hiltwrap:processor-room')
}
```

### Usage

To use the annotations provided by the `:library:hiltwrap` module, simply annotate your classes, properties, or functions as needed. The processors will generate the necessary Hilt modules.

### Annotations

The `:library:hiltwrap` module includes the following annotations:

1. **`@HiltWrapBindModule`**
    - Marks a class as a Hilt module for binding dependencies.
    - **Properties:**
        - `superType`: The super type or interface that the annotated class will bind to.
        - `component`: The Hilt component in which the module will be installed.

2. **`@HiltWrapRoomModule`**
    - Marks a class as a Hilt module for Room database integration.
    - **Properties:**
        - `installInComponent`: The Hilt component in which the module will be installed.
        - `databaseName`: The name of the Room database.

3. **`@HiltWrapRoomDao`**
    - Marks a property or function as a Room DAO for Hilt dependency injection.
    - **Properties:**
        - `scope`: The Hilt scope in which the DAO will be provided.
