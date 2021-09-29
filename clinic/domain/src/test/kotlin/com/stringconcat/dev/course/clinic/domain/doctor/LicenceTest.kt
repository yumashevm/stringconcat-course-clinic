package com.stringconcat.dev.course.clinic.domain.doctor

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class LicenceTest {

    @ParameterizedTest
    @ValueSource(strings = ["АА-ММ 123456", "ММ-АА 123456"])
    fun `correct format - successfully created`(input: String) {
        Licence.from(input) shouldBeRight { it.num shouldBe input }
    }

    @ParameterizedTest
    @ValueSource(strings = ["АD-ММ 123456", " dsadsad", "123456", "АА-ММ 12345"])
    fun `incorrect format - should be fail`(input: String) {
        Licence.from(input) shouldBeLeft LicenceFormatError
    }
}