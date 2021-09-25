package com.stringconcat.dev.course.clinic.domain.doctor

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class DoctorNameTest {

    @Test
    fun shouldBeOk() {
        val surname = "Иванов"
        val name = "Иван"
        val patronymic = "Иванович"

        val result = DoctorName.from(surname, name, patronymic)

        result.shouldBeRight {
            it.surname shouldBe surname
            it.name shouldBe name
            it.patronymic shouldBe patronymic
        }

        val resultWithoutPatronymic = DoctorName.from(surname, name)

        resultWithoutPatronymic.shouldBeRight {
            it.surname shouldBe surname
            it.name shouldBe name
            it.patronymic shouldBe ""
        }
    }

    @Test
    fun shouldBeNotOk() {
        val surname = "t"
        val name = "t"
        val patronymic = "t"

        val result = DoctorName.from(surname, name, patronymic)

        result.shouldBeLeft(DoctorNameError)

        val resultLong = DoctorName.from(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus tortor sed massa dignissim," +
                    " vitae pulvinar justo auctor dolor.",
            name
        )

        resultLong.shouldBeLeft(DoctorNameError)

        val resultLong2 = DoctorName.from(
            surname = "Иванов",
            name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus tortor sed massa dignissim," +
                    " vitae pulvinar justo auctor dolor.",
        )

        resultLong2.shouldBeLeft(DoctorNameError)

        val resultLong3 = DoctorName.from(
            surname = "Иванов",
            name = "Иван",
            patronymic = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus tortor sed" +
                    " massa dignissim, vitae pulvinar justo auctor dolor.",
        )

        resultLong3.shouldBeLeft(DoctorNameError)
    }
}