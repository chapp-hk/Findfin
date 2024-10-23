package org.chapp.findfin.core.design.ui.foundation.search

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.parcelize.Parcelize

/**
 * A stable interface representing the state of the AppSearchBar.
 */
@Stable
interface AppSearchBarState {
    /**
     * The current query value of the search bar.
     */
    var value: String

    /**
     * The placeholder text to be displayed in the search bar.
     */
    var placeholder: String
}

private class AppSearchBarStateImpl(
    value: String,
    placeholder: String,
) : AppSearchBarState {
    override var value by mutableStateOf(value)
    override var placeholder by mutableStateOf(placeholder)
}

/**
 * A Parcelable data class used to save and restore the state of the AppSearchBar.
 *
 * @property value The current query value of the search bar.
 * @property placeholder The placeholder text to be displayed in the search bar.
 */
@Parcelize
private data class AppSearchBarStateSaveable(
    val value: String,
    val placeholder: String,
) : Parcelable

/**
 * A composable function that remembers the state of the AppSearchBar.
 *
 * @param value The initial query value of the search bar.
 * @param placeholder The initial placeholder text to be displayed in the search bar.
 * @return An instance of [AppSearchBarState] with the remembered state.
 */
@Composable
fun rememberAppSearchBarState(
    value: String = "",
    placeholder: String = "",
) = rememberSaveable(saver = appSearchBarStateSaver) {
    AppSearchBarStateImpl(
        value = value,
        placeholder = placeholder,
    )
}

private val appSearchBarStateSaver =
    Saver<AppSearchBarState, AppSearchBarStateSaveable>(
        save = { state ->
            AppSearchBarStateSaveable(
                value = state.value,
                placeholder = state.placeholder,
            )
        },
        restore = { saveable ->
            AppSearchBarStateImpl(
                value = saveable.value,
                placeholder = saveable.placeholder,
            )
        },
    )
