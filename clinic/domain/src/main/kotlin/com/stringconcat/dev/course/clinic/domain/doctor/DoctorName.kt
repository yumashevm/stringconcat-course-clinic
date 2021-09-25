package com.stringconcat.dev.course.clinic.domain.doctor

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.stringconcat.dev.course.common.types.base.ValueObject
import com.stringconcat.dev.course.common.types.error.BusinessError

data class DoctorName(val surname: String, val name: String, var patronymic: String = "") : ValueObject {
    companion object {
        private const val NAME_MAX_LENGTH = 128
        private const val NAME_MIN_LENGTH = 2
        fun from(surname: String, name: String, patronymic: String = ""): Either<DoctorNameError, DoctorName> =
            if (checkName(surname, name, patronymic)) {
                DoctorName(surname, name, patronymic).right()
            } else {
                DoctorNameError.left()
            }

        private fun checkLength(name: String): Boolean {
            return name.isNotBlank() && name.length > NAME_MIN_LENGTH && name.length < NAME_MAX_LENGTH
        }

        private fun checkName(surname: String, name: String, patronymic: String): Boolean {
            return checkLength(surname) && checkLength(name) && (patronymic.isBlank() || checkLength(patronymic))
        }
    }
}

object DoctorNameError : BusinessError