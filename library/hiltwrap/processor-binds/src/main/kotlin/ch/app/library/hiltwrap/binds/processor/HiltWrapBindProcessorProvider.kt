package ch.app.library.hiltwrap.binds.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class HiltWrapBindProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return HiltWrapBindSymbolProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
        )
    }
}
