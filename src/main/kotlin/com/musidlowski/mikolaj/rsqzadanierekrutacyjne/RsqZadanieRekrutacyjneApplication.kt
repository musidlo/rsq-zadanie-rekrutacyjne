package com.musidlowski.mikolaj.rsqzadanierekrutacyjne

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.*
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository.DoctorRepository
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository.PatientRepository
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository.VisitRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.time.LocalDateTime

@SpringBootApplication
class RsqZadanieRekrutacyjneApplication {

    @Bean
    fun init(doctorRepository: DoctorRepository, patientRepository: PatientRepository, visitRepository: VisitRepository) = CommandLineRunner {
        val doctor1 = doctorRepository.save(Doctor("Jan", "Kowalski", Specialization.DENTIST))
        val doctor2 = doctorRepository.save(Doctor("Przemysław", "Zalewski", Specialization.PHYSIOTHERAPIST))

        val patient1 = patientRepository.save(Patient("Ryszard", "Szymczak", Address("Poznan", "12-345", "Długa", 14, 7)))
        val patient2 = patientRepository.save(Patient("Dominik", "Kozłowski", Address("Poznan", "54-321", "Krótka", 10)))

        visitRepository.save(Visit(doctor1, patient1, LocalDateTime.of(2020, 6, 30, 12, 0), "Gabinet 15"))
        visitRepository.save(Visit(doctor2, patient2, LocalDateTime.of(2020, 7, 1, 15, 30), "Gabinet 4"))
    }
}

fun main(args: Array<String>) {
    runApplication<RsqZadanieRekrutacyjneApplication>(*args)
}
