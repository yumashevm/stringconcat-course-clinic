package com.stringconcat.dev.course.clinic.domain.doctor

interface DoctorIdGenerator {
    fun generate(): DoctorId
}