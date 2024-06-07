# Data layer

## Separating data layer into different Gradle modules for local and remote data sources. This is a form of modularization and it has several benefits:

1. **Separation of Concerns**:\
Each module can focus on a single aspect of your application, making the code easier to understand and maintain.

2. **Reusability**:\
Modules can be reused across different projects. If you have a module that provides common functionality, you can easily include it in any project that needs it.

3. **Faster Build Times**:\
Gradle can build modules in parallel, and if a module hasn't changed, Gradle can use the cached version from a previous build. This can significantly reduce your build times.

4. **Better Collaboration**:\
If you're working in a team, different team members can work on different modules without stepping on each other's toes.

Example of data layer modularization:

```
- data_api (module for repository)
- data_local (local data source module)
- data_remote (remote data source module)
```

In this structure, each module has a specific role:

- Thr `data_api` module contains the repository interface and the repository implementation.
- The `data_local` module contains the local data source of your application.
- The `data_remote` module contains the remote data source of your application.

This structure allows you to isolate the dependencies and responsibilities of each module, making your code easier to test and maintain. However, keep in mind that this approach might increase the complexity of your project configuration, as each module will have its own build.gradle file and dependencies.
