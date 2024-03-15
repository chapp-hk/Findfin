package ch.app.hk.bank.locator.testing.instrument

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider

fun getResourceString(
    @StringRes resId: Int,
): String = ApplicationProvider.getApplicationContext<Context>().getString(resId)
