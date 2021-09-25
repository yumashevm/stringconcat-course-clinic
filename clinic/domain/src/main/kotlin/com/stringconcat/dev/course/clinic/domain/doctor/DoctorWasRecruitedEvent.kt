package com.stringconcat.dev.course.clinic.domain.doctor

import com.stringconcat.dev.course.common.types.base.DomainEvent

data class DoctorWasRecruitedEvent(val doctorId: DoctorId) : DomainEvent()
