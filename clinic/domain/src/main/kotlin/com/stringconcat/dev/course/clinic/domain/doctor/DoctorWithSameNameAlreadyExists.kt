package com.stringconcat.dev.course.clinic.domain.doctor

import com.stringconcat.dev.course.common.types.common.PersonName

interface DoctorWithSameNameAlreadyExists {
    fun check(name: PersonName): Boolean
}
