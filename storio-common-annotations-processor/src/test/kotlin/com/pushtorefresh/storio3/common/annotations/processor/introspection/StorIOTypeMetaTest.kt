package com.pushtorefresh.storio3.common.annotations.processor.introspection

import com.nhaarman.mockito_kotlin.mock
import com.squareup.javapoet.ClassName
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import javax.lang.model.element.Element

class StorIOTypeMetaTest {

    private lateinit var annotationMock: Annotation

    @Before
    fun setUp() {
        annotationMock = mock()
    }

    @Test
    fun constructor() {
        // when
        val nonNullAnnotationClass = ClassName.get("androidx.core.content.ContextCompat", "NonNull")
        val typeMeta = StorIOTestTypeMeta("TEST", "TEST", annotationMock, true, nonNullAnnotationClass)

        // then
        assertThat(typeMeta.simpleName).isEqualTo("TEST")
        assertThat(typeMeta.packageName).isEqualTo("TEST")
        assertThat(typeMeta.storIOType).isEqualTo(annotationMock)
        assertThat(typeMeta.needsCreator).isEqualTo(true)
    }

    @Test
    fun equalsAndHashCode() {
        EqualsVerifier.forClass(StorIOTypeMeta::class.java)
                .suppress(Warning.REFERENCE_EQUALITY)
                .usingGetClass()
                .verify()
    }

    @Test
    fun toStringValitadion() {
        // given
        val nonNullAnnotationClass = ClassName.get("androidx.core.content.ContextCompat", "NonNull")
        val typeMeta = StorIOTestTypeMeta("TEST", "TEST", annotationMock, true, nonNullAnnotationClass)
        val expectedString = "StorIOTypeMeta(simpleName='TEST', packageName='TEST'," +
                " storIOType=$annotationMock, needsCreator=true, creator=null," +
                " columns=${typeMeta.columns})"

        // when
        val toString = typeMeta.toString()

        // then
        assertThat(expectedString).isEqualTo(toString)
    }

}

class StorIOTestColumnMeta(enclosingElement: Element,
                           element: Element,
                           elementName: String,
                           javaType: JavaType,
                           storIOColumn: Annotation)
    : StorIOColumnMeta<Annotation>(
        enclosingElement,
        element,
        elementName,
        javaType,
        storIOColumn)

class StorIOTestTypeMeta(simpleName: String,
                         packageName: String,
                         storIOType: Annotation,
                         needCreator: Boolean,
                         nonNullAnnotationClass: ClassName
) : StorIOTypeMeta<Annotation, StorIOTestColumnMeta>(
        simpleName,
        packageName,
        storIOType,
        needCreator,
        nonNullAnnotationClass
) {
    override val generateTableClass: Boolean
        get() = false
}