package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.*
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository.VisitRepository
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.serviceImp.VisitServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.dao.EmptyResultDataAccessException
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VisitServiceImplTest {

    @Mock
    private lateinit var visitRepository: VisitRepository

    @InjectMocks
    private lateinit var visitServiceImpl: VisitServiceImpl

    private lateinit var doctor: Doctor

    private lateinit var patient: Patient

    private lateinit var visit: Visit

    private lateinit var newDate: LocalDateTime

    private val id = 1L

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    fun beforeTest() {
        doctor = Doctor("Jane", "Doe", Specialization.PHYSIOTHERAPIST)
        patient = Patient("John", "Doe", Address("Poznan", "12-345", "Długa", 1, 2))
        visit = Visit(doctor, patient, LocalDateTime.of(2020, 6, 30, 12, 0), "Gabinet 4")
        newDate = LocalDateTime.of(2020, 6, 30, 12, 0)
    }

    @Test
    fun `when find all visits then return list of visits`() {
        val listOfVisits = listOf(visit)
        Mockito.`when`(visitRepository.findAll()).thenReturn(listOfVisits)
        val visitsFound = visitServiceImpl.findAllVisits()
        assertThat(visitsFound).isEqualTo(listOfVisits)
    }

    @Test
    fun `when find visit by id with valid id then return visit`() {
        Mockito.`when`(visitRepository.findById(id)).thenReturn(Optional.of(visit))
        val visitFound = visitServiceImpl.findVisitById(id)
        assertThat(visitFound).isEqualTo(visit)
    }

    @Test
    fun `when find visit by id with not found id then return null`() {
        Mockito.`when`(visitRepository.findById(id)).thenReturn(Optional.empty())
        val visitFound = visitServiceImpl.findVisitById(id)
        assertThat(visitFound).isEqualTo(null)
    }

    @Test
    fun `when find visits by patient with valid id then return list of visits`() {
        val listOfVisits = listOf(visit)
        Mockito.`when`(visitRepository.findByPatient_Id(id)).thenReturn(listOfVisits)
        val visitsFound = visitServiceImpl.findVisitsByPatientId(id)
        assertThat(visitsFound).isEqualTo(listOfVisits)
    }

    @Test
    fun `when find visits by patient id with not found id then return null`() {
        Mockito.`when`(visitRepository.findByPatient_Id(id)).thenReturn(emptyList())
        val visitsFound = visitServiceImpl.findVisitsByPatientId(id)
        assertThat(visitsFound).isEqualTo(emptyList<Visit>())
    }

    @Test
    fun `when save visit then return visit`() {
        Mockito.`when`(visitRepository.save(visit)).thenReturn(visit)
        val visitCreated = visitServiceImpl.saveVisit(visit)
        assertThat(visitCreated).isEqualTo(visit)
    }

    @Test
    fun `when update visit with valid id then return true`() {
        Mockito.`when`(visitRepository.findById(id)).thenReturn(Optional.of(visit))
        Mockito.`when`(visitRepository.save(visit)).thenReturn(visit)
        assertTrue(visitServiceImpl.updateVisitTimeById(id, newDate))
    }

    @Test
    fun `when update visit with not found id then return false`() {
        Mockito.`when`(visitRepository.findById(id)).thenReturn(Optional.empty())
        assertFalse(visitServiceImpl.updateVisitTimeById(id, newDate))
    }

    @Test
    fun `when delete visit with valid id then return true`() {
        Mockito.doNothing().`when`(visitRepository).deleteById(id)
        Mockito.`when`(visitRepository.findById(id)).thenReturn(Optional.of(visit))
        assertTrue(visitServiceImpl.deleteVisitById(id))
    }

    @Test
    fun `when delete patient with not found id then return false`() {
        Mockito.`when`(visitRepository.deleteById(id)).thenThrow(EmptyResultDataAccessException(0))
        assertFalse(visitServiceImpl.deleteVisitById(id))
    }
}