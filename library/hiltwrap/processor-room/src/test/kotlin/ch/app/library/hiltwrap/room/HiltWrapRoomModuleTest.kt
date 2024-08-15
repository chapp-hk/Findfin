package ch.app.library.hiltwrap.room

import ch.app.hk.bank.locator.testing.util.readResourceAsText
import ch.app.library.hiltwrap.room.processor.HiltWrapRoomProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import io.kotest.matchers.shouldBe
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@OptIn(ExperimentalCompilerApi::class)
@DisplayName("@HiltWrapRoomModule tests")
class HiltWrapRoomModuleTest {
    private val generatedCodePath = "/kotlin/ch/app/library/hiltwrap/room/"
    private val generatedFileNamePostfix = "HiltWrapRoomModule"

    @Test
    fun `test @HiltWrapRoomModule with default parameter values`() {
        val testClassName = "TestDatabaseDefaultParameters"
        val kotlinSource =
            SourceFile.kotlin(
                name = "$testClassName.kt",
                contents = readResourceAsText("source/test-default-params.source"),
            )

        val compilation = compileKotlinSource(kotlinSource)
        compilation.compile()

        File(
            "${compilation.kspSourcesDir.path}${generatedCodePath}${testClassName}$generatedFileNamePostfix.kt",
        ).readText() shouldBe
            readResourceAsText("expected/generated-default-params.source")
    }

    @Test
    fun `test @HiltWrapRoomModule with custom parameter values`() {
        val testClassName = "TestDatabaseCustomParameters"
        val kotlinSource =
            SourceFile.kotlin(
                name = "$testClassName.kt",
                contents = readResourceAsText("source/test-custom-params.source"),
            )

        val compilation = compileKotlinSource(kotlinSource)
        compilation.compile()

        File(
            "${compilation.kspSourcesDir.path}${generatedCodePath}${testClassName}$generatedFileNamePostfix.kt",
        ).readText() shouldBe
            readResourceAsText("expected/generated-custom-params.source")
    }

    @Test
    fun `test @HiltWrapRoomModule with empty databaseName`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestEmptyDatabaseName.kt",
                contents = readResourceAsText("source/test-empty-database-name.source"),
            )

        val compilation = compileKotlinSource(kotlinSource)
        compilation.compile().exitCode shouldBe KotlinCompilation.ExitCode.COMPILATION_ERROR
    }

    @Test
    fun `test @HiltWrapRoomModule without @Database`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestWithoutDatabaseAnnotation.kt",
                contents = readResourceAsText("source/test-without-database-annotation.source"),
            )

        val compilation = compileKotlinSource(kotlinSource)
        compilation.compile().exitCode shouldBe KotlinCompilation.ExitCode.COMPILATION_ERROR
    }

    @Test
    fun `test @HiltWrapRoomModule with custom scope not annotated with @Scope`() {
        val kotlinSource =
            SourceFile.kotlin(
                name = "TestCustomScopeNotAnnotatedWithScope.kt",
                contents = readResourceAsText("source/test-custom-scope-without-annotated.source"),
            )

        val compilation = compileKotlinSource(kotlinSource)
        compilation.compile().exitCode shouldBe KotlinCompilation.ExitCode.COMPILATION_ERROR
    }

    private fun compileKotlinSource(source: SourceFile): KotlinCompilation {
        return KotlinCompilation().apply {
            inheritClassPath = true
            workingDir = File("build/tmp/Kotlin-Compilation")
            sources = listOf(source)
            symbolProcessorProviders = listOf(HiltWrapRoomProcessorProvider())
        }
    }
}
