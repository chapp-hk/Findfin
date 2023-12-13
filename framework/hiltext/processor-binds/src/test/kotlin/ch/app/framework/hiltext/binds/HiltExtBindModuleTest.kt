package ch.app.framework.hiltext.binds

import ch.app.framework.hiltext.binds.processor.HiltExtBindProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@DisplayName("@HiltExtBindModule tests")
class HiltExtBindModuleTest {
    @Test
    fun `test @HiltExtBindModule with default parameter values`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassDefault.kt",
                contents = """
                package ch.app.framework.hiltext

                import ch.app.framework.hiltext.annotation.HiltExtBindModule
                import javax.inject.Inject

                interface TestInterface

                @HiltExtBindModule
                class TestClassDefault @Inject constructor() : TestInterface
            """,
            )

        val compilation = compileKotlinSource(kotlinSource)
        compilation.compile()

        val generatedFilePath = "kotlin/ch/app/framework/hiltext/TestClassDefaultHiltExtBindModule.kt"
        File("${compilation.kspSourcesDir.path}/$generatedFilePath")
            .readText() shouldBe
            """
            package ch.app.framework.hiltext

            import dagger.Binds
            import dagger.Module
            import dagger.hilt.InstallIn
            import dagger.hilt.components.SingletonComponent

            @Module
            @InstallIn(SingletonComponent::class)
            internal interface TestClassDefaultHiltExtBindModule {
                @Binds
                public fun bindToSuperType(`impl`: TestClassDefault): TestInterface
            }

            """.trimIndent()
    }

    @Test
    fun `test @HiltExtBindModule with parameter values`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassParameters.kt",
                contents = """
                package ch.app.framework.hiltext

                import ch.app.framework.hiltext.annotation.HiltExtBindModule
                import dagger.hilt.DefineComponent
                import javax.inject.Inject
                import javax.inject.Singleton

                @DefineComponent
                interface TestComponent

                interface TestInterface

                @Singleton
                @HiltExtBindModule(
                    superType = TestInterface::class,
                    component = TestComponent::class,
                )
                class TestClassParameters @Inject constructor() : TestInterface
            """,
            )

        val compilation = compileKotlinSource(kotlinSource)
        compilation.compile()

        val generatedFilePath = "kotlin/ch/app/framework/hiltext/TestClassParametersHiltExtBindModule.kt"
        File("${compilation.kspSourcesDir.path}/$generatedFilePath")
            .readText() shouldBe
            """
            package ch.app.framework.hiltext

            import dagger.Binds
            import dagger.Module
            import dagger.hilt.InstallIn
            import javax.inject.Singleton

            @Module
            @InstallIn(TestComponent::class)
            internal interface TestClassParametersHiltExtBindModule {
                @Binds
                @Singleton
                public fun bindToSuperType(`impl`: TestClassParameters): TestInterface
            }

            """.trimIndent()
    }

    @Test
    fun `test @HiltExtBindModule without @Inject should compile error`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassParameters.kt",
                contents = """
                package ch.app.framework.hiltext

                import ch.app.framework.hiltext.annotation.HiltExtBindModule
                import dagger.hilt.DefineComponent

                @DefineComponent
                interface TestComponent

                interface TestInterface

                @HiltExtBindModule(
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
    fun `test @HiltExtBindModule with invalid super type should compile error`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassParameters.kt",
                contents = """
                package ch.app.framework.hiltext

                import ch.app.framework.hiltext.annotation.HiltExtBindModule
                import javax.inject.Inject
                import dagger.hilt.DefineComponent

                @DefineComponent
                interface TestComponent

                interface TestInterface

                @HiltExtBindModule(
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
    fun `test @HiltExtBindModule with invalid component should compile error`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestClassParameters.kt",
                contents = """
                package ch.app.framework.hiltext

                import ch.app.framework.hiltext.annotation.HiltExtBindModule
                import javax.inject.Inject

                interface TestComponent

                interface TestInterface

                @HiltExtBindModule(
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
            symbolProcessorProviders = listOf(HiltExtBindProcessorProvider())
        }
    }
}
