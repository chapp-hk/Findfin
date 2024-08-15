package ch.app.library.hiltwrap.room.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class HiltWrapRoomProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return HiltWrapRoomProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
        )
    }
}
