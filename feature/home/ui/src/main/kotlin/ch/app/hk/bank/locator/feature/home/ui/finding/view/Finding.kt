package ch.app.hk.bank.locator.feature.home.ui.finding.view

import android.content.res.Configuration
import android.graphics.Color
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.modifier.contentDescription
import ch.app.hk.bank.locator.core.imageloader.ImageView
import ch.app.hk.bank.locator.feature.home.ui.R

@Composable
internal fun Finding(
    modifier: Modifier = Modifier,
    onFindYourBank: () -> Unit = {},
    onFindBankOrAtms: () -> Unit = {},
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(id = R.string.home_title_finding),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            ButtonWithImageAndTitle(
                modifier = Modifier.weight(1f),
                imageResourceId = R.drawable.home_img_find_bank,
                titleResourceId = R.string.home_label_find_your_bank,
                contentDescription = stringResource(id = R.string.home_label_find_your_bank),
                onClick = onFindYourBank,
            )

            ButtonWithImageAndTitle(
                modifier = Modifier.weight(1f),
                imageResourceId = R.drawable.home_img_find_atm,
                titleResourceId = R.string.home_label_find_bank_or_atms,
                contentDescription = stringResource(id = R.string.home_label_find_bank_or_atms),
                onClick = onFindBankOrAtms,
            )
        }
    }
}

@Composable
private fun ButtonWithImageAndTitle(
    modifier: Modifier = Modifier,
    @DrawableRes imageResourceId: Int,
    @StringRes titleResourceId: Int,
    contentDescription: String,
    onClick: () -> Unit,
) {
    Column(modifier = modifier) {
        Button(
            modifier =
                Modifier
                    .aspectRatio(1f)
                    .contentDescription(contentDescription),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            onClick = onClick,
        ) {
            ImageView(
                modifier = Modifier.fillMaxWidth(),
                source = imageResourceId,
                contentDescription = contentDescription,
            )
        }

        Text(
            modifier =
                Modifier
                    .padding(top = 8.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = titleResourceId),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelMedium,
        )
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
private fun FindingPreview() {
    AppContent {
        Finding()
    }
}
