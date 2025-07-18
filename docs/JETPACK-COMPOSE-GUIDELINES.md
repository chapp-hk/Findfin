# 🎨 Jetpack Compose Guidelines

## Overview

Jetpack Compose is the modern UI toolkit for building native Android UIs declaratively. This guide establishes comprehensive guidelines for using Compose effectively within the Findfin project architecture, ensuring consistency, performance, and maintainability across all UI modules.

### Core Principles

1. **Declarative UI**: Describe what the UI should look like for any given state
2. **Unidirectional Data Flow**: Data flows down, events flow up
3. **Single Source of Truth**: State should have a single owner
4. **Composition over Inheritance**: Build complex UIs by composing simple components
5. **Stateless Composables**: Prefer stateless composables for reusability

---

## 🏗️ Composable Architecture Patterns

### Stateless vs Stateful Composables

#### ✅ Stateless Composables (Preferred)
```kotlin
@Composable
fun BankCard(
    bank: Bank,
    isSelected: Boolean,
    onCardClick: (Bank) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onCardClick(bank) },
        colors = if (isSelected) selectedColors else defaultColors
    ) {
        // Card content
    }
}
```

#### ⚠️ Stateful Composables (Limited Use)
```kotlin
@Composable
fun SearchableList(
    items: List<Item>,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    
    Column(modifier = modifier) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
        LazyColumn {
            items(items.filter { it.matches(searchQuery) }) { item ->
                ItemRow(item = item)
            }
        }
    }
}
```

### Screen-Level Architecture

#### Screen Composable Pattern
```kotlin
@Composable
fun BankSelectionScreen(
    viewModel: BankSelectionViewModel = hiltViewModel(),
    onNavigateToDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    BankSelectionContent(
        uiState = uiState,
        onBankSelected = viewModel::selectBank,
        onNavigateToDetails = onNavigateToDetails
    )
}

@Composable
private fun BankSelectionContent(
    uiState: BankSelectionUiState,
    onBankSelected: (Bank) -> Unit,
    onNavigateToDetails: (String) -> Unit
) {
    when (uiState) {
        is BankSelectionUiState.Loading -> LoadingIndicator()
        is BankSelectionUiState.Success -> BankList(...)
        is BankSelectionUiState.Error -> ErrorState(...)
    }
}
```

### Component Hierarchy

```
Screen (Stateful)
├── Content (Stateless)
│   ├── Section Components
│   │   ├── Atomic Components
│   │   └── Molecule Components
│   └── Layout Components
```

---

## 🔄 State Management

### State Hoisting

#### ✅ Proper State Hoisting
```kotlin
@Composable
fun LocationSearchScreen(viewModel: LocationViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    val locations by viewModel.locations.collectAsStateWithLifecycle()
    
    LocationSearchContent(
        query = searchQuery,
        onQueryChange = { query ->
            searchQuery = query
            viewModel.searchLocations(query)
        },
        locations = locations,
        onLocationSelected = viewModel::selectLocation
    )
}
```

### State Management Anti-Patterns

#### ❌ Don't: Multiple Sources of Truth
```kotlin
// Bad: Both local state and ViewModel state
@Composable
fun BadExample(viewModel: MyViewModel) {
    var localCount by remember { mutableStateOf(0) }
    val viewModelCount by viewModel.count.collectAsState()
    
    // Confusing: Which count is the source of truth?
}
```

#### ❌ Don't: State in Composables That Should Be Stateless
```kotlin
// Bad: UI list component managing its own selection state
@Composable
fun BankList(banks: List<Bank>) {
    var selectedBank by remember { mutableStateOf<Bank?>(null) }
    // Selection state should be managed by parent
}
```

### Lifecycle-Aware State Collection

```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel = hiltViewModel()) {
    // ✅ Lifecycle-aware collection
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    // ❌ Not lifecycle-aware
    // val uiState by viewModel.uiState.collectAsState()
}
```

---

## 🎯 UI State Patterns

### Sealed Class UI States

```kotlin
sealed interface BankListUiState {
    data object Loading : BankListUiState
    
    data class Success(
        val banks: List<Bank>,
        val selectedBank: Bank? = null,
        val isRefreshing: Boolean = false
    ) : BankListUiState
    
    data class Error(
        val message: String,
        val canRetry: Boolean = true
    ) : BankListUiState
}
```

### Data Classes for Complex States

```kotlin
data class LocationUiState(
    val currentLocation: Location? = null,
    val nearbyBanks: List<Bank> = emptyList(),
    val isLocationPermissionGranted: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
```

### Event Handling

```kotlin
// ✅ Use sealed classes for one-time events
sealed interface BankSelectionEvent {
    data object NavigateToHome : BankSelectionEvent
    data class ShowError(val message: String) : BankSelectionEvent
    data class NavigateToDetails(val bankId: String) : BankSelectionEvent
}

@Composable
fun BankSelectionScreen(viewModel: BankSelectionViewModel = hiltViewModel()) {
    val events by viewModel.events.collectAsStateWithLifecycle()
    
    LaunchedEffect(events) {
        when (events) {
            is BankSelectionEvent.NavigateToHome -> { /* Navigate */ }
            is BankSelectionEvent.ShowError -> { /* Show snackbar */ }
            is BankSelectionEvent.NavigateToDetails -> { /* Navigate with ID */ }
        }
    }
}
```

---

## 🎨 Design System Integration

### Theme Usage

```kotlin
@Composable
fun BankCard(
    bank: Bank,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = FindfinTheme.colors.surface,
            contentColor = FindfinTheme.colors.onSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = FindfinTheme.elevation.small
        )
    ) {
        Text(
            text = bank.name,
            style = FindfinTheme.typography.titleMedium,
            color = FindfinTheme.colors.onSurface
        )
    }
}
```

### Custom Component Extensions

```kotlin
// Extend existing components with theme-aware defaults
@Composable
fun FindfinButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = FindfinTheme.colors.primary,
        contentColor = FindfinTheme.colors.onPrimary
    ),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        shape = FindfinTheme.shapes.medium,
        content = content
    )
}
```

---

## ⚡ Performance Guidelines

### Remember Usage

#### ✅ Proper Remember Usage
```kotlin
@Composable
fun ExpensiveCalculationComponent(
    data: List<ComplexData>
) {
    val processedData = remember(data) {
        data.processComplexCalculation()
    }
    
    val lazyListState = rememberLazyListState()
    
    LazyColumn(state = lazyListState) {
        items(processedData) { item ->
            ItemRow(item = item)
        }
    }
}
```

#### ❌ Remember Anti-Patterns
```kotlin
@Composable
fun BadRememberUsage(data: String) {
    // Bad: Remember without keys when needed
    val processed = remember { data.process() }
    
    // Bad: Remembering mutable state incorrectly
    val list = remember { mutableListOf<String>() }
    list.add(data) // This will cause issues
}
```

### Lazy Layout Optimization

```kotlin
@Composable
fun OptimizedBankList(
    banks: List<Bank>,
    onBankClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = banks,
            key = { bank -> bank.id } // ✅ Always provide keys
        ) { bank ->
            BankListItem(
                bank = bank,
                onClick = { onBankClick(bank.id) }
            )
        }
    }
}
```

### Composition Optimization

```kotlin
// ✅ Use stable parameters
@Stable
data class BankUiModel(
    val id: String,
    val name: String,
    val logoUrl: String,
    val isSelected: Boolean
)

// ✅ Use immutable collections
@Composable
fun BankGrid(banks: ImmutableList<BankUiModel>) {
    // Compose can optimize recomposition
}
```

---

## 🧪 Testing Compose UIs

### Testing Stateless Composables

```kotlin
@Test
fun bankCard_displaysCorrectInformation() {
    val testBank = Bank(
        id = "test-id",
        name = "Test Bank",
        logoUrl = "https://example.com/logo.png"
    )
    
    composeTestRule.setContent {
        FindfinTheme {
            BankCard(
                bank = testBank,
                isSelected = false,
                onCardClick = { }
            )
        }
    }
    
    composeTestRule
        .onNodeWithText("Test Bank")
        .assertIsDisplayed()
}
```

### Testing User Interactions

```kotlin
@Test
fun bankCard_clickTriggersCallback() {
    var clickedBank: Bank? = null
    val testBank = Bank(id = "test", name = "Test Bank")
    
    composeTestRule.setContent {
        BankCard(
            bank = testBank,
            isSelected = false,
            onCardClick = { clickedBank = it }
        )
    }
    
    composeTestRule
        .onNodeWithText("Test Bank")
        .performClick()
    
    assertEquals(testBank, clickedBank)
}
```

### Testing with ViewModels

```kotlin
@Test
fun bankSelectionScreen_displaysLoadingState() {
    val viewModel = FakeBankSelectionViewModel()
    viewModel.setLoadingState()
    
    composeTestRule.setContent {
        BankSelectionScreen(viewModel = viewModel)
    }
    
    composeTestRule
        .onNodeWithTag("loading_indicator")
        .assertIsDisplayed()
}
```

---

## 🚀 Navigation Integration

### Navigation with Compose

```kotlin
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("bank_selection") {
            BankSelectionScreen(
                onNavigateToDetails = { bankId ->
                    navController.navigate("bank_details/$bankId")
                }
            )
        }
        
        composable(
            "bank_details/{bankId}",
            arguments = listOf(navArgument("bankId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bankId = backStackEntry.arguments?.getString("bankId") ?: ""
            BankDetailsScreen(
                bankId = bankId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
```

### Type-Safe Navigation (Recommended)

```kotlin
@Serializable
object BankSelection

@Serializable
data class BankDetails(val bankId: String)

// Usage
navController.navigate(BankDetails(bankId = "123"))
```

---

## 🔧 Error Handling

### Error States in UI

```kotlin
@Composable
fun ErrorState(
    message: String,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = null,
            tint = FindfinTheme.colors.error,
            modifier = Modifier.size(48.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = message,
            style = FindfinTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(16.dp))
            FindfinButton(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}
```

### Error Boundaries

```kotlin
@Composable
fun SafeComposable(
    content: @Composable () -> Unit
) {
    var hasError by remember { mutableStateOf(false) }
    
    if (hasError) {
        ErrorState(
            message = "Something went wrong",
            onRetry = { hasError = false }
        )
    } else {
        try {
            content()
        } catch (e: Exception) {
            LaunchedEffect(e) {
                hasError = true
            }
        }
    }
}
```

---

## 📏 Best Practices

### ✅ Do's

1. **Use Preview Composables**
```kotlin
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BankCardPreview() {
    FindfinTheme {
        BankCard(
            bank = Bank.preview(),
            isSelected = false,
            onCardClick = { }
        )
    }
}
```

2. **Provide Meaningful Content Descriptions**
```kotlin
Icon(
    imageVector = Icons.Default.Search,
    contentDescription = "Search banks",
    modifier = Modifier.clickable { onSearchClick() }
)
```

3. **Use Semantic Properties**
```kotlin
Text(
    text = balance,
    modifier = Modifier.semantics {
        contentDescription = "Account balance: $balance"
        role = Role.Button
    }
)
```

4. **Handle Loading and Empty States**
```kotlin
when {
    isLoading -> LoadingIndicator()
    banks.isEmpty() -> EmptyState(message = "No banks found")
    else -> BankList(banks = banks)
}
```

5. **Use Stable Collections for Performance**
```kotlin
@Composable
fun BankList(banks: ImmutableList<Bank>) {
    // Compose can optimize recomposition
}
```

### ❌ Don'ts

1. **Don't Use Side Effects in Composables**
```kotlin
// ❌ Bad
@Composable
fun BadComposable() {
    // Side effects in composition
    performNetworkCall()
}

// ✅ Good
@Composable
fun GoodComposable() {
    LaunchedEffect(Unit) {
        performNetworkCall()
    }
}
```

2. **Don't Mutate State Directly in Composables**
```kotlin
// ❌ Bad
@Composable
fun BadStateUsage(list: MutableList<Item>) {
    list.add(Item()) // Direct mutation
}

// ✅ Good
@Composable
fun GoodStateUsage(
    items: List<Item>,
    onAddItem: (Item) -> Unit
) {
    // Let parent handle state changes
}
```

3. **Don't Ignore Composition Lifecycle**
```kotlin
// ❌ Bad
@Composable
fun BadLifecycle() {
    val timer = Timer() // Created on every recomposition
}

// ✅ Good
@Composable
fun GoodLifecycle() {
    val timer = remember { Timer() }
    DisposableEffect(Unit) {
        onDispose { timer.cancel() }
    }
}
```

---

## 📚 Additional Resources

### Findfin-Specific Patterns
- Refer to `core/design/theme` for theme implementation
- Check `core/design/ui-foundation` for base components
- Follow existing patterns in `feature/*/presentation` modules

### External Resources
- [Compose API Guidelines](https://github.com/androidx/androidx/blob/androidx-main/compose/docs/compose-api-guidelines.md)
- [Compose Performance](https://developer.android.com/jetpack/compose/performance)
- [Testing Compose](https://developer.android.com/jetpack/compose/testing)

---

*This document should be updated as new patterns emerge and the codebase evolves. All team members should contribute to keeping these guidelines current and relevant.* 