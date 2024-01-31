package ch.app.framework.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.properties
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.ext.list.withoutAnnotationOf
import com.lemonappdev.konsist.api.verify.assertTrue
import io.ktor.resources.Resource
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Konsist kotlinx serialization tests")
class KotlinxSerializationTest {
    @Test
    fun `classes annotated with 'Serializable' have all properties annotated with 'SerialName' or 'JsonNames'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withAnnotationOf(Serializable::class)
            .withoutAnnotationOf(Resource::class)
            .properties()
            .assertTrue {
                it.hasAnnotationOf(SerialName::class) ||
                    @OptIn(ExperimentalSerializationApi::class)
                    it.hasAnnotationOf(JsonNames::class)
            }
    }
}
