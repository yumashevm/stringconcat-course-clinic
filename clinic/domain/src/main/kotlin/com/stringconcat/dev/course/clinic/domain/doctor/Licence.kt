package com.stringconcat.dev.course.clinic.domain.doctor

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.stringconcat.dev.course.common.types.base.ValueObject
import com.stringconcat.dev.course.common.types.error.BusinessError

data class Licence internal constructor(val num: String) : ValueObject {
    companion object {
        private val regex = Regex("^[а-яА-Я]{2}-[а-яА-Я]{2} \\d{6}\$")

        fun from(num: String): Either<LicenceFormatError, Licence> {
            val upperCaseNum = num.uppercase()
            return if (regex.matches(upperCaseNum)) {
                Licence(upperCaseNum).right()
            } else {
                LicenceFormatError.left()
            }
        }
    }
}

object LicenceFormatError : BusinessError
