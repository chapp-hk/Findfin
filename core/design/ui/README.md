# Pre-built UI components

## AppTextField
Text input field
# Usage
```kotlin
val state = rememberAppTextFieldState()
AppTextField(
    modifier = Modifier,
    state = state,
    trailingIcon = {
        IconButton(
            onClick = { /* do something */ },
        ) {
            // put your icon composable here
        }
    },
    visualTransformation = VisualTransformation.None,
    keyboardOptions = KeyboardOptions.Default,
)
```

## PasswordTextField
Password input field
# Usage
```kotlin
val state = rememberAppTextFieldState()
PasswordTextField(
    modifier = Modifier,
    state = state,
)
```
