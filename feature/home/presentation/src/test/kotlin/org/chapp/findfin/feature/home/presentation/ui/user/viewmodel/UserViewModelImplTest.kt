package org.chapp.findfin.feature.home.presentation.ui.user.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.design.ui.foundation.ScreenState
import org.chapp.findfin.feature.auth.data.repo.user.model.UserModel
import org.chapp.findfin.feature.auth.data.repo.user.repository.UserRepository
import org.chapp.findfin.feature.home.presentation.ui.user.state.UserUiState
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("UserViewModelImpl unit tests")
internal class UserViewModelImplTest {
    private val userRepository = mockk<UserRepository>()

    @ParameterizedTest(
        name =
            "when userRepository.getCurrentUser() returns {0}, " +
                "then AuthEntryViewModelImpl.uiState should be {1}",
    )
    @ArgumentsSource(UiStateArgumentProvider::class)
    fun `test uiState`(
        mockCurrentUser: UserModel?,
        expectedResult: UserUiState,
    ) = runTest {
        coEvery { userRepository.getCurrentUser() } returns mockCurrentUser

        val userViewModel = createUserViewModel()

        userViewModel.uiState.test {
            awaitItem() shouldBe ScreenState.Loading
            awaitItem() shouldBe ScreenState.Success(expectedResult)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private class UiStateArgumentProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
            val mockUserModel = mockk<UserModel>()

            return Stream.of(
                Arguments.arguments(
                    null,
                    UserUiState.Guest,
                ),
                Arguments.arguments(
                    mockUserModel,
                    UserUiState.Authorized(mockUserModel),
                ),
            )
        }
    }

    private fun createUserViewModel() = UserViewModelImpl(userRepository = userRepository)
}
