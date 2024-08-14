# Konsist module
Using [Konsist](https://docs.konsist.lemonappdev.com/getting-started/readme) to check declaration and architecture

## Declaration checks
- properties are declared before functions
- companion object is last declaration in the class
- no empty files allowed
- package name must match file path
- classes annotated with 'Serializable' have all properties annotated with 'SerialName' or 'JsonNames'

## Architecture checks
- 'src' directory should be 'kotlin'
- classes with 'UseCase' suffix should have single 'public operator' method named 'invoke'
