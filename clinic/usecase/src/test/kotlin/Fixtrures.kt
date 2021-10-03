import arrow.core.Either
import com.stringconcat.dev.course.clinic.domain.doctor.Doctor
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorId
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorIdGenerator
import com.stringconcat.dev.course.clinic.domain.doctor.DoctorWithSameNameAlreadyExists
import com.stringconcat.dev.course.clinic.domain.doctor.Licence
import com.stringconcat.dev.course.clinic.usecase.doctor.scenario.DoctorPersister
import com.stringconcat.dev.course.common.types.common.PersonName
import kotlin.random.Random.Default.nextLong

class TestDoctorPersister : HashMap<DoctorId, Doctor>(), DoctorPersister {
    override fun save(doctor: Doctor) {
        this[doctor.id] = doctor
    }
}

class TestDoctorIdGenerator : DoctorIdGenerator {
    override fun generate(): DoctorId {
        return DoctorId(nextLong())
    }
}

class TestDoctorWithSameNameNotExists : DoctorWithSameNameAlreadyExists {
    override fun check(name: PersonName): Boolean {
        return false
    }
}

class TestDoctorWithSameNameAlreadyExists : DoctorWithSameNameAlreadyExists {
    override fun check(name: PersonName): Boolean {
        return true
    }
}

fun validDoctorName(): PersonName {
    val result = PersonName.from("Иванов", "Иван", "Иванович")
    check(result is Either.Right<PersonName>)
    return result.b
}

fun validLicence(): Licence {
    val result = Licence.from("АА-ББ 123456")
    check(result is Either.Right<Licence>)
    return result.b
}