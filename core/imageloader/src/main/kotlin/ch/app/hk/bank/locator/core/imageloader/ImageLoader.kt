package ch.app.hk.bank.locator.core.imageloader

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.AsyncImage
import okhttp3.HttpUrl
import java.io.File
import java.nio.ByteBuffer

/**
 * Displays an image from a given source using the AsyncImage composable from the Coil library.
 * This function abstracts the Coil AsyncImage API, providing a simplified interface for image loading in Compose UIs.
 *
 * @param modifier Modifier applied to the AsyncImage composable for customization.
 * @param source The image source, supported data types are:
 *   - [String] (mapped to a [Uri])
 *   - [Uri] ("android.resource", "content", "file", "http", and "https" schemes only)
 *   - [HttpUrl]
 *   - [File]
 *   - [DrawableRes]
 *   - [Drawable]
 *   - [Bitmap]
 *   - [ByteArray]
 *   - [ByteBuffer]
 * @param placeholder A painter to be displayed during the image loading process. Can be used to display a temporary image until the actual image is loaded.
 * @param error A painter that is displayed if an error occurs during the image loading process. Useful for providing feedback to the user in case of loading failures.
 * @param contentDescription Text used by accessibility services to describe what the image represents. Null indicates that the image is decorative.
 */
@Composable
fun ImageView(
    modifier: Modifier = Modifier,
    source: Any,
    placeholder: Painter? = null,
    error: Painter? = null,
    contentDescription: String? = null,
) {
    AsyncImage(
        modifier = modifier,
        model = source,
        placeholder = placeholder,
        error = error,
        contentDescription = contentDescription,
    )
}
