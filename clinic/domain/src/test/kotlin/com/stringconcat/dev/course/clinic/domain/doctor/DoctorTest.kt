package com.stringconcat.dev.course.clinic.domain.doctor

import arrow.core.Either
import com.stringconcat.dev.course.common.types.common.PersonName
import io.kotest.assertions.arrow.either.shouldBeLeft
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

        val name = PersonName.from("Иванов", "Иван", "Иванович")
        check(name is Either.Right<PersonName>)
        val licence = Licence.from("АА-ББ 123456")
        check(licence is Either.Right<Licence>)
        val specialization = Specialization.THERAPIST

        val result = Doctor.recruitDoctor(
            idGenerator = idGenerator,
            name = name.b,
            licence = licence.b,
            specialization = specialization,
            DoctorWithSameNameDoesntExists
        )

        result shouldBeRight {
            it.id shouldBe doctorId
            it.name shouldBe name.b
            it.specialization shouldBe specialization
            it.licence shouldBe licence.b
            it.popEvents() shouldContainExactly listOf(DoctorWasRecruitedEvent(doctorId))
        }
    }


    @Test
    fun `recruit Doctor - already exists`() {

        val name = PersonName.from("Иванов", "Иван", "Иванович")
        check(name is Either.Right<PersonName>)
        val licence = Licence.from("АА-ББ 123456")
        check(licence is Either.Right<Licence>)
        val specialization = Specialization.THERAPIST

        val result = Doctor.recruitDoctor(
            idGenerator = idGenerator,
            name = name.b,
            licence = licence.b,
            specialization = specialization,
            DoctorWithSameNameExists
        )

        result.shouldBeLeft(DoctorWithSameNameAlreadyExistsError(name.b))
    }

    private object DoctorWithSameNameDoesntExists : DoctorWithSameNameAlreadyExists {
        override fun check(name: PersonName) = false
    }

    private object DoctorWithSameNameExists : DoctorWithSameNameAlreadyExists {
        override fun check(name: PersonName) = true
    }
}