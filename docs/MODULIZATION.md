## Module structure

Using a simple level module structure like `:core:design-ui` and `:core:design-theme` versus a more nested structure can depend on the complexity and size of project.

If the project is large and has many interrelated components, using a nested structure can help keep things organized and clear. It can make it easier to understand the relationships between different parts of the project and can help prevent circular dependencies.

On the other hand, if the project is smaller or the components are largely independent, a simple level module structure might be sufficient and easier to manage.

In terms of performance, there's no significant difference between the two approaches. The main considerations should be readability, maintainability, and the logical organization of the project.

The goal is to create a structure that makes sense for the project.

## Testing module

The organization of modules in a project can depend on the specific needs and structure of your project.

1. Keeping the `:testing` modules in the `:core` folder can help keep all the core-related code (including tests) in one place. This can make it easier to find and manage the code related to the core functionality of the project.

2. Keeping the `:testing` modules outside of the `:core` folder can help separate the testing code from the production code. This can make it easier to manage dependencies and build configurations, as can ensure that testing dependencies are only included in the testing modules and not in the production modules.
