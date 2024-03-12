package ch.app.hk.bank.locator.testing.google.play.services.task

import com.google.android.gms.tasks.Task
import io.mockk.every
import io.mockk.mockk
import java.lang.Exception

fun <T> mockTaskError(mockException: Exception) =
    mockk<Task<T>> {
        every { isComplete } returns true
        every { exception } returns mockException
    }

fun <T> mockTaskResult(mockResult: T) =
    mockk<Task<T>> {
        every { isComplete } returns true
        every { exception } returns null
        every { isCanceled } returns false
        every { result } returns mockResult
    }
