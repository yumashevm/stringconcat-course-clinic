package com.stringconcat.dev.course.common.types.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.stringconcat.dev.course.common.types.base.ValueObject
import com.stringconcat.dev.course.common.types.error.BusinessError

data class PersonName internal constructor(val surname: String, val name: String, val patronymic: String) :
    ValueObject {
    companion object {
        private const val NAME_MAX_LENGTH = 128
        private const val NAME_MIN_LENGTH = 2
        fun from(surname: String, name: String, patronymic: String = ""): Either<PersonNameError, PersonName> =
            if (checkName(surname, name, patronymic)) {
                PersonName(surname, name, patronymic).right()
            } else {
                PersonNameError.left()
            }

        private fun checkLength(name: String): Boolean {
            return name.isNotBlank() && name.length > NAME_MIN_LENGTH && name.length < NAME_MAX_LENGTH
        }

        private fun checkName(surname: String, name: String, patronymic: String): Boolean {
            return checkLength(surname) && checkLength(name) && (patronymic.isBlank() || checkLength(patronymic))
        }
    }
}

object PersonNameError : BusinessError