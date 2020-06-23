package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDateTime

@DataJpaTest
class VisitRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var visitRepository: VisitRepository

    private lateinit var doctor: Doctor

    private lateinit var patient: Patient

    private lateinit var visit: Visit

    @BeforeEach
    fun setUp() {
        doctor = Doctor("John", "Doe", Specialization.PHYSIOTHERAPIST)
        entityManager.persist(doctor)

        patient = Patient("Jane", "Doe",
                Address("Poznan", "12-345", "Długa", 12, 4))
        entityManager.persist(patient)

        visit = Visit(doctor, patient,
                LocalDateTime.of(2020, 6, 30, 12, 0, 0), "Gabient 3")
        entityManager.persist(visit)
        entityManager.flush()
    }

    @Test
    fun `When find visit for valid patient id then return Visit`() {
        val found: List<Visit> = visitRepository.findByPatient_Id(patient.id!!)

        for (foundVisit in found) {
            assertThat(foundVisit.patient).isEqualTo(patient)
        }
    }

    @Test
    fun `When find visit for invalid patient id then return empty list`() {
        val found: List<Visit> = visitRepository.findByPatient_Id(Long.MAX_VALUE)

        assertTrue(found.isEmpty())
    }
}