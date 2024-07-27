package ch.app.hk.bank.locator.feature.home.ui.user.view

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.feature.auth.data.repo.user.model.UserModel
import ch.app.hk.bank.locator.feature.home.ui.R

@Composable
internal fun Authorized(
    modifier: Modifier = Modifier,
    user: UserModel,
) {
    Row(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.Rounded.AccountCircle,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface),
            contentDescription = stringResource(id = R.string.home_content_description_avatar),
        )

        Column(
            modifier = Modifier.padding(start = 8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.home_label_greeting),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            )

            Text(
                text = user.email,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = Color.WHITE.toLong(),
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = Color.BLACK.toLong(),
)
@Composable
private fun GuestPreview() {
    AppContent {
        Authorized(
            user =
                UserModel(
                    displayName = "",
                    email = "test@register.com",
                    isEmailVerified = false,
                ),
        )
    }
}
