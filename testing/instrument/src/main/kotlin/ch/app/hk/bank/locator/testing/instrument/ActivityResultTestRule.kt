package ch.app.hk.bank.locator.testing.instrument

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope
import androidx.core.app.ActivityOptionsCompat
import org.junit.rules.ExternalResource

/**
 * A JUnit rule that provides a mock implementation of the ActivityResultRegistryOwner interface.
 * This rule is used to test components that interact with the Activity Result API.
 *
 * @property context The context used to create intents for the mocked activity results.
 */
@RestrictTo(Scope.TESTS)
class ActivityResultTestRule(private val context: Context) : ExternalResource() {
    /**
     * The intent that was launched by the ActivityResultRegistry.
     * This field is set when an activity result is launched and can be used to verify the intent's properties.
     */
    var launchedIntent: Intent? = null
        private set

    /**
     * Creates a mock implementation of the ActivityResultRegistryOwner interface.
     * This mock implementation captures the launched intent and dispatches a mocked activity result.
     *
     * @param mockedActivityResult The mocked activity result to be dispatched.
     * @return An instance of ActivityResultRegistryOwner with a mocked ActivityResultRegistry.
     */
    fun <R> registryOwner(mockedActivityResult: R) =
        object : ActivityResultRegistryOwner {
            override val activityResultRegistry: ActivityResultRegistry =
                object : ActivityResultRegistry() {
                    override fun <I, O> onLaunch(
                        requestCode: Int,
                        contract: ActivityResultContract<I, O>,
                        input: I,
                        options: ActivityOptionsCompat?,
                    ) {
                        launchedIntent = contract.createIntent(context, input)
                        dispatchResult(requestCode, mockedActivityResult)
                    }
                }
        }
}
