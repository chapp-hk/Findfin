package ch.app.library.hiltext.room.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class HiltExtRoomProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return HiltExtRoomProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
        )
    }
}
