package com.stringconcat.dev.course.clinic.domain.doctor

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.stringconcat.dev.course.common.types.base.AggregateRoot
import com.stringconcat.dev.course.common.types.base.Version
import com.stringconcat.dev.course.common.types.common.PersonName
import com.stringconcat.dev.course.common.types.error.BusinessError

class Doctor internal constructor(
    id: DoctorId,
    val name: PersonName,
    val licence: Licence,
    val specialization: Specialization,
    version: Version
) : AggregateRoot<DoctorId>(id, version) {

    companion object {
        fun recruitDoctor(
            idGenerator: DoctorIdGenerator,
            name: PersonName,
            licence: Licence,
            specialization: Specialization,
            doctorWithSameNameAlreadyExists: DoctorWithSameNameAlreadyExists
        ): Either<DoctorWithSameNameAlreadyExistsError, Doctor> =
            if (doctorWithSameNameAlreadyExists.check(name)) {
                DoctorWithSameNameAlreadyExistsError(name).left()
            } else {
                Doctor(
                    id = idGenerator.generate(),
                    name = name,
                    licence = licence,
                    specialization = specialization,
                    version = Version.new()
                ).apply {
                    addEvent(DoctorWasRecruitedEvent(this.id))
                }.right()
            }
    }
}

data class DoctorWithSameNameAlreadyExistsError(val name: PersonName) : BusinessError
