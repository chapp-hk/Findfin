package ch.app.hk.bank.locator.testing.instrument

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider

@RestrictTo(Scope.TESTS)
fun getResourceString(
    @StringRes resId: Int,
): String = ApplicationProvider.getApplicationContext<Context>().getString(resId)
