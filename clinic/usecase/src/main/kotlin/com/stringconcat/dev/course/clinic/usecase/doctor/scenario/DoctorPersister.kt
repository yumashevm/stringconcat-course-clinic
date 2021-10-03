package com.stringconcat.dev.course.clinic.usecase.doctor.scenario

import com.stringconcat.dev.course.clinic.domain.doctor.Doctor

interface DoctorPersister {
    fun save(doctor: Doctor)
}
