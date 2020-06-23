package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Address
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Patient
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository.PatientRepository
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.serviceImp.PatientServiceImpl
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
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientServiceImplTest {


    @Mock
    private lateinit var patientRepository: PatientRepository

    @InjectMocks
    private lateinit var patientServiceImpl: PatientServiceImpl

    private lateinit var patient: Patient

    private val id = 1L

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    fun beforeTest() {
        patient = Patient("John", "Doe", Address("Poznan", "12-345", "Długa", 1, 2))
    }

    @Test
    fun `when find all patients then return list of patients`() {
        val listOfPatients = listOf(patient)
        Mockito.`when`(patientRepository.findAll()).thenReturn(listOfPatients)
        val patientsFound = patientServiceImpl.findAllPatients()
        assertThat(patientsFound).isEqualTo(listOfPatients)
    }

    @Test
    fun `when find patient by id with valid id then return patient`() {
        Mockito.`when`(patientRepository.findById(id)).thenReturn(Optional.of(patient))
        val patientFound = patientServiceImpl.findPatientById(id)
        assertThat(patientFound).isEqualTo(patient)
    }

    @Test
    fun `when find patient by id with not found id then return null`() {
        Mockito.`when`(patientRepository.findById(id)).thenReturn(Optional.empty())
        val patientFound = patientServiceImpl.findPatientById(id)
        assertThat(patientFound).isEqualTo(null)
    }

    @Test
    fun `when save patient then return patient`() {
        Mockito.`when`(patientRepository.save(patient)).thenReturn(patient)
        val patientCreated = patientServiceImpl.savePatient(patient)
        assertThat(patientCreated).isEqualTo(patient)
    }

    @Test
    fun `when update patient with valid id then return true`() {
        Mockito.`when`(patientRepository.findById(id)).thenReturn(Optional.of(patient))
        Mockito.`when`(patientRepository.save(patient)).thenReturn(patient)
        assertTrue(patientServiceImpl.updatePatient(id, patient))
    }

    @Test
    fun `when update patient with not found id then return false`() {
        Mockito.`when`(patientRepository.findById(id)).thenReturn(Optional.empty())
        assertFalse(patientServiceImpl.updatePatient(id, patient))
    }

    @Test
    fun `when delete patient with valid id then return true`() {
        Mockito.doNothing().`when`(patientRepository).deleteById(id)
        assertTrue(patientServiceImpl.deletePatientById(id))
    }

    @Test
    fun `when delete patient with not found id then return false`() {
        Mockito.`when`(patientRepository.deleteById(id)).thenThrow(EmptyResultDataAccessException(0))
        assertFalse(patientServiceImpl.deletePatientById(id))
    }
}