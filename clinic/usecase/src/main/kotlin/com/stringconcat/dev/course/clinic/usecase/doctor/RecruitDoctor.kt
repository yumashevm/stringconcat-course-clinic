package com.stringconcat.dev.course.clinic.usecase.doctor

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorId
import com.stringconcat.dev.course.clinic.domain.doctor.Licence
import com.stringconcat.dev.course.clinic.domain.doctor.LicenceFormatError
import com.stringconcat.dev.course.clinic.domain.doctor.Specialization
import com.stringconcat.dev.course.common.types.common.PersonName
import com.stringconcat.dev.course.common.types.common.PersonNameError

interface RecruitDoctor {
    fun execute(request: RecruitDoctorRequest): Either<RecruitDoctorError, DoctorId>
}

sealed class RecruitDoctorError(open val message: String) {
    object AlreadyExists : RecruitDoctorError("Doctor already exists")
}

data class RecruitDoctorRequest internal constructor(
    val doctorName: PersonName,
    val licence: Licence,
    val specialization: Specialization
) {
    companion object {

        fun from(
            surname: String,
            name: String,
            patronymic: String,
            licence: String,
            specialization: Specialization
        ): Either<InvalidRecruitDoctorRequest, RecruitDoctorRequest> {
            return tupled(
                PersonName.from(surname, name, patronymic).mapLeft { it.toError() },
                Licence.from(licence).mapLeft { it.toError() }
            ).map { params ->
                RecruitDoctorRequest(params.a, params.b, specialization)
            }
        }
    }
}

data class InvalidRecruitDoctorRequest(val message: String)

fun PersonNameError.toError() = InvalidRecruitDoctorRequest("Incorrect doctor name")
fun LicenceFormatError.toError() = InvalidRecruitDoctorRequest("Incorrect licence")