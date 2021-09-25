package com.stringconcat.dev.course.clinic.domain.doctor

import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class DoctorTest {

    val doctorId = DoctorId(Random.nextLong())

    private val idGenerator = object : DoctorIdGenerator {
        override fun generate() = doctorId
    }

    @Test
    fun `recruit Doctor - success`() {

        val name = DoctorName("Иванов", "Иван", "Иванович")
        val licence = Licence("АА-ББ 123456")
        val specialization = Specialization.THERAPIST

        val result = Doctor.recruitDoctor(
            idGenerator = idGenerator,
            name = name,
            specialization = specialization,
            licence = licence
        )

        result shouldBeRight {
            it.id shouldBe doctorId
            it.name shouldBe name
            it.specialization shouldBe specialization
            it.licence shouldBe licence
            it.popEvents() shouldContainExactly listOf(DoctorWasRecruitedEvent(doctorId))
        }
    }
}