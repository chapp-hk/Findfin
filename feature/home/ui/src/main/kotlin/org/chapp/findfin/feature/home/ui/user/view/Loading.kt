package org.chapp.findfin.feature.home.ui.user.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.chapp.findfin.core.design.ui.loading.CirclePlaceHolder
import org.chapp.findfin.core.design.ui.loading.RectanglePlaceHolder
import org.chapp.findfin.core.design.ui.modifier.contentDescription
import org.chapp.findfin.feature.home.ui.R

@Composable
internal fun Loading(modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .contentDescription(stringResource(id = R.string.home_content_description_loading))
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        CirclePlaceHolder(size = 50.dp)

        VerticalDivider(
            modifier =
                Modifier
                    .height(50.dp),
            thickness = 8.dp,
            color = Color.Transparent,
        )

        RectanglePlaceHolder(
            modifier =
                Modifier
                    .align(Alignment.CenterVertically),
            cornerRadius = 10.dp,
            width = 200.dp,
            height = 30.dp,
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0XFFFFFFFF,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0X00000000,
)
@Composable
private fun LoadingPreview() {
    Loading()
}
