# Class definition

## Repository, Helper, and Util Classes

### Repository Classes

A repository class is part of the Data Access Layer in an application. It provides a way to retrieve, store, and manipulate data from a data source like a database or a remote server. The repository acts as a bridge between the data source and the rest of the application, abstracting the underlying data access complexities.

```kotlin
interface UserRepository {
    fun getUser(userId: String): User
    fun saveUser(user: User)
}
```

### Helper Classes

A helper class, also known as a utility class, typically provides static methods that perform common functions throughout the application. These methods are usually stateless and can be called without creating an instance of the helper class. Helper classes can be used to perform operations like string manipulation, mathematical calculations, or common UI updates.

```kotlin
object MathHelper {
    fun calculateAverage(numbers: List<Int>): Double {
        return numbers.sum() / numbers.size.toDouble()
    }
}
```

### Util Classes

Util classes are similar to helper classes. They contain utility methods that are used across the application. These methods are generally static and stateless. They perform common, often reusable functions that don't belong to any other class. Util classes can be used for operations like date and time conversion, network checks, or common mathematical calculations.

```kotlin
object DateUtil {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
```

## Difference Between Helper and Util Classes

While both helper and util classes are used to provide common functionalities, there are some subtle differences between them:

1. **Helper Classes**: These are typically used to provide methods that assist in accomplishing a specific task related to a certain object or module. They are often used when the methods don't fit into the object-oriented structure of the application. For example, a `DatabaseHelper` class might provide methods to open, close, and perform operations on a database.

2. **Util Classes**: These are used to provide a set of static methods that perform common, often reusable functions that don't belong to any other class. They are usually stateless and can be called without creating an instance of the class. For example, a `StringUtils` class might provide static methods for string manipulation like trimming, padding, or checking if a string is empty.

In summary, while both helper and util classes are used to provide common functionalities, helper classes are typically tied to a specific object or module, while util classes provide more general, reusable functionalities.
