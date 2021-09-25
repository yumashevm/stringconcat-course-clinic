package com.stringconcat.dev.course.clinic.domain.doctor

import arrow.core.Either
import arrow.core.right
import com.stringconcat.dev.course.common.types.base.AggregateRoot
import com.stringconcat.dev.course.common.types.base.Version
import com.stringconcat.dev.course.common.types.error.BusinessError

class Doctor internal constructor(
    id: DoctorId,
    val name: DoctorName,
    val licence: Licence,
    val specialization: Specialization,
    version: Version
) : AggregateRoot<DoctorId>(id, version) {

    companion object {
        fun recruitDoctor(
            idGenerator: DoctorIdGenerator,
            name: DoctorName,
            licence: Licence,
            specialization: Specialization
        ): Either<BusinessError, Doctor> =
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