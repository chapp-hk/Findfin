package org.chapp.findfin.core.design.ui.search

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.parcelize.Parcelize

@Stable
interface AppSearchBarState {
    var value: String
    var placeholder: String
}

private class AppSearchBarStateImpl(
    value: String,
    placeholder: String,
) : AppSearchBarState {
    override var value by mutableStateOf(value)
    override var placeholder by mutableStateOf(placeholder)
}

@Parcelize
private data class AppSearchBarStateSaveable(
    val value: String,
    val placeholder: String,
) : Parcelable

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
