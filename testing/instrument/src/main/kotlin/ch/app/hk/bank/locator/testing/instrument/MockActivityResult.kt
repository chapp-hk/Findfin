package ch.app.hk.bank.locator.testing.instrument

import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityOptionsCompat

fun <O> mockActivityResult(result: O) =
    object : ActivityResultRegistryOwner {
        override val activityResultRegistry: ActivityResultRegistry =
            object : ActivityResultRegistry() {
                override fun <I, O> onLaunch(
                    requestCode: Int,
                    contract: ActivityResultContract<I, O>,
                    input: I,
                    options: ActivityOptionsCompat?,
                ) {
                    dispatchResult(requestCode, result)
                }
            }
    }
