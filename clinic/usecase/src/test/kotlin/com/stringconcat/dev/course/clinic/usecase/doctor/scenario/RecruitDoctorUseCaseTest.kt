package com.stringconcat.dev.course.clinic.usecase.doctor.scenario

import TestDoctorIdGenerator
import TestDoctorPersister
import TestDoctorWithSameNameNotExists
import arrow.core.Either
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorId
import com.stringconcat.dev.course.clinic.domain.doctor.Specialization
import com.stringconcat.dev.course.clinic.usecase.doctor.RecruitDoctorRequest
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import validDoctorName
import validLicence

internal class RecruitDoctorUseCaseTest {

    @Test
    fun successfullyRecruitDoctorUTest() {
        val doctorPersister = TestDoctorPersister()
        val doctorName = validDoctorName()
        val licence = validLicence()
        var result = RecruitDoctorUseCase(
            doctorPersister = doctorPersister,
            doctorIdGenerator = TestDoctorIdGenerator(),
            doctorWithSameNameAlreadyExists = TestDoctorWithSameNameNotExists()
        ).execute(
            RecruitDoctorRequest(
                doctorName = doctorName,
                licence = licence,
                specialization = Specialization.THERAPIST
            )
        )
        check(result is Either.Right<DoctorId>)
        val doctor = doctorPersister[result.b]
        doctor.shouldNotBeNull()
        doctor.name shouldBe doctorName
        doctor.licence shouldBe licence
        doctor.specialization shouldBe Specialization.THERAPIST
    }
}