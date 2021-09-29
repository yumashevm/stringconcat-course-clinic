package com.stringconcat.dev.course.common.types.common

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class PersonNameTest {

    @Test
    fun `correct name - successfully created`() {
        val surname = "Иванов"
        val name = "Иван"
        val patronymic = "Иванович"

        val result = PersonName.from(surname, name, patronymic)

        result.shouldBeRight {
            it.surname shouldBe surname
            it.name shouldBe name
            it.patronymic shouldBe patronymic
        }

        val resultWithoutPatronymic = PersonName.from(surname, name)

        resultWithoutPatronymic.shouldBeRight {
            it.surname shouldBe surname
            it.name shouldBe name
            it.patronymic shouldBe ""
        }
    }

    @Test
    fun `incorrect name - DoctorNameError was returned`() {
        val surname = "t"
        val name = "t"
        val patronymic = "t"

        val result = PersonName.from(surname, name, patronymic)

        result.shouldBeLeft(PersonNameError)

        val resultLong = PersonName.from(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus tortor sed massa dignissim," +
                    " vitae pulvinar justo auctor dolor.",
            name
        )

        resultLong.shouldBeLeft(PersonNameError)

        val resultLong2 = PersonName.from(
            surname = "Иванов",
            name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus tortor sed massa dignissim," +
                    " vitae pulvinar justo auctor dolor.",
        )

        resultLong2.shouldBeLeft(PersonNameError)

        val resultLong3 = PersonName.from(
            surname = "Иванов",
            name = "Иван",
            patronymic = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus tortor sed" +
                    " massa dignissim, vitae pulvinar justo auctor dolor.",
        )

        resultLong3.shouldBeLeft(PersonNameError)
    }
}