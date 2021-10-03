package com.stringconcat.dev.course.clinic.usecase.doctor.scenario

import arrow.core.Either
import com.stringconcat.dev.course.clinic.domain.doctor.Doctor
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorId
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorIdGenerator
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorWithSameNameAlreadyExists
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorWithSameNameAlreadyExistsError
import com.stringconcat.dev.course.clinic.usecase.doctor.RecruitDoctor
import com.stringconcat.dev.course.clinic.usecase.doctor.RecruitDoctorError
import com.stringconcat.dev.course.clinic.usecase.doctor.RecruitDoctorRequest

class RecruitDoctorUseCase(
    private val doctorPersister: DoctorPersister,
    private val doctorIdGenerator: DoctorIdGenerator,
    private val doctorWithSameNameAlreadyExists: DoctorWithSameNameAlreadyExists
) : RecruitDoctor {
    override fun execute(request: RecruitDoctorRequest): Either<RecruitDoctorError, DoctorId> =
        Doctor.recruitDoctor(
            idGenerator = doctorIdGenerator,
            name = request.doctorName,
            licence = request.licence,
            specialization = request.specialization,
            doctorWithSameNameAlreadyExists = doctorWithSameNameAlreadyExists
        ).mapLeft { e -> e.toError() }
            .map { doctor ->
                doctorPersister.save(doctor)
                doctor.id
            }
}

fun DoctorWithSameNameAlreadyExistsError.toError() = RecruitDoctorError.AlreadyExists