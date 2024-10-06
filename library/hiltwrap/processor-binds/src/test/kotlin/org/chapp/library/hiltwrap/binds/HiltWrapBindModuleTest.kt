package org.chapp.library.hiltwrap.binds

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import io.kotest.matchers.shouldBe
import org.chapp.library.hiltwrap.binds.processor.HiltWrapBindProcessorProvider
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@OptIn(ExperimentalCompilerApi::class)
@DisplayName("@HiltWrapBindModule tests")
class HiltWrapBindModuleTest {
    @Test
    fun `test @HiltWrapBindModule with default parameter values`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassDefault.kt",
                contents = """
                package org.chapp.library.hiltwrap

                import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
                import javax.inject.Inject

                interface TestInterface

                @HiltWrapBindModule
                class TestClassDefault @Inject constructor() : TestInterface
            """,
            )

        val compilation = compileKotlinSource(kotlinSource)
        compilation.compile()

        val generatedFilePath = "kotlin/org/chapp/library/hiltwrap/TestClassDefaultHiltWrapBindModule.kt"
        File("${compilation.kspSourcesDir.path}/$generatedFilePath")
            .readText() shouldBe
            """
            package org.chapp.library.hiltwrap

            import dagger.Binds
            import dagger.Module
            import dagger.hilt.InstallIn
            import dagger.hilt.components.SingletonComponent

            @Module
            @InstallIn(SingletonComponent::class)
            internal interface TestClassDefaultHiltWrapBindModule {
                @Binds
                public fun bindToSuperType(`impl`: TestClassDefault): TestInterface
            }

            """.trimIndent()
    }

    @Test
    fun `test @HiltWrapBindModule with parameter values`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassParameters.kt",
                contents = """
                package org.chapp.library.hiltwrap

                import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
                import dagger.hilt.DefineComponent
                import javax.inject.Inject
                import javax.inject.Singleton

                @DefineComponent
                interface TestComponent

                interface TestInterface

                @Singleton
                @HiltWrapBindModule(
                    superType = TestInterface::class,
                    component = TestComponent::class,
                )
                class TestClassParameters @Inject constructor() : TestInterface
            """,
            )

        val compilation = compileKotlinSource(kotlinSource)
        compilation.compile()

        val generatedFilePath = "kotlin/org/chapp/library/hiltwrap/TestClassParametersHiltWrapBindModule.kt"
        File("${compilation.kspSourcesDir.path}/$generatedFilePath")
            .readText() shouldBe
            """
            package org.chapp.library.hiltwrap

            import dagger.Binds
            import dagger.Module
            import dagger.hilt.InstallIn
            import javax.inject.Singleton

            @Module
            @InstallIn(TestComponent::class)
            internal interface TestClassParametersHiltWrapBindModule {
                @Binds
                @Singleton
                public fun bindToSuperType(`impl`: TestClassParameters): TestInterface
            }

            """.trimIndent()
    }

    @Test
    fun `test @HiltWrapBindModule without @Inject should compile error`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassParameters.kt",
                contents = """
                package org.chapp.library.hiltwrap

                import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
                import dagger.hilt.DefineComponent

                @DefineComponent
                interface TestComponent

                interface TestInterface

                @HiltWrapBindModule(
                    superType = TestInterface::class,
                    component = TestComponent::class,
                )
                class TestClassParameters constructor() : TestInterface
            """,
            )

        val compilation = compileKotlinSource(kotlinSource)
        val result = compilation.compile()

        result.exitCode shouldBe KotlinCompilation.ExitCode.COMPILATION_ERROR
    }

    @Test
    fun `test @HiltWrapBindModule with invalid super type should compile error`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassParameters.kt",
                contents = """
                package org.chapp.library.hiltwrap

                import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
                import javax.inject.Inject
                import dagger.hilt.DefineComponent

                @DefineComponent
                interface TestComponent

                interface TestInterface

                @HiltWrapBindModule(
                    superType = Int::class,
                    component = TestComponent::class,
                )
                class TestClassParameters @Inject constructor() : TestInterface
            """,
            )

        val compilation = compileKotlinSource(kotlinSource)
        val result = compilation.compile()

        result.exitCode shouldBe KotlinCompilation.ExitCode.COMPILATION_ERROR
    }

    @Test
    fun `test @HiltWrapBindModule with invalid component should compile error`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassParameters.kt",
                contents = """
                package org.chapp.library.hiltwrap

                import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
                import javax.inject.Inject

                interface TestComponent

                interface TestInterface

                @HiltWrapBindModule(
                    component = TestComponent::class,
                )
                class TestClassParameters @Inject constructor() : TestInterface
            """,
            )

        val compilation = compileKotlinSource(kotlinSource)
        val result = compilation.compile()

        result.exitCode shouldBe KotlinCompilation.ExitCode.COMPILATION_ERROR
    }

    private fun compileKotlinSource(source: SourceFile): KotlinCompilation {
        return KotlinCompilation().apply {
            inheritClassPath = true
            workingDir = File("build/tmp/Kotlin-Compilation")
            sources = listOf(source)
            symbolProcessorProviders = listOf(HiltWrapBindProcessorProvider())
        }
    }
}
