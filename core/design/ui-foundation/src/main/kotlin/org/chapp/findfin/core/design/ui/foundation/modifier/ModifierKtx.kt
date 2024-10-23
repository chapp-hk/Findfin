package org.chapp.findfin.core.design.ui.foundation.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

/**
 * Adds a content description to a [Modifier] for accessibility purposes.
 * The content description is used by screen readers to describe the UI element to users with visual impairments.
 *
 * @param contentDescription A [String] representing the content description to be added to the [Modifier].
 * @return A [Modifier] with the added content description.
 *
 * Example usage:
 * ```
 * val myModifier = Modifier.contentDescription("This is a button")
 * ```
 */
fun Modifier.contentDescription(contentDescription: String): Modifier =
    this.then(
        Modifier.semantics { this.contentDescription = contentDescription },
    )
