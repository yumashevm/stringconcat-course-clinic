package com.stringconcat.dev.course.clinic.usecase.doctor.scenario

import com.stringconcat.dev.course.clinic.domain.doctor.Doctor
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorId
import com.stringconcat.dev.course.common.types.common.PersonName

interface DoctorExtractor {

    fun findAll(): List<Doctor>

    fun findByName(name: PersonName): Doctor?

    fun findById(id: DoctorId): Doctor?
}