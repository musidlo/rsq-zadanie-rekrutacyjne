package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Doctor
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Specialization
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository.DoctorRepository
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.serviceImp.DoctorServiceImpl
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
class DoctorServiceImplTest {

    @Mock
    private lateinit var doctorRepository: DoctorRepository

    @InjectMocks
    private lateinit var doctorServiceImpl: DoctorServiceImpl

    private lateinit var doctor: Doctor

    private val id = 1L

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    fun beforeTest() {
        doctor = Doctor("John", "Doe", Specialization.PHYSIOTHERAPIST)
    }

    @Test
    fun `when find doctor by id with valid id then return doctor`() {
        Mockito.`when`(doctorRepository.findById(id)).thenReturn(Optional.of(doctor))
        val doctorFound = doctorServiceImpl.findDoctorById(id)
        assertThat(doctorFound).isEqualTo(doctor)
    }

    @Test
    fun `when find doctor by id with not found id then return null`() {
        Mockito.`when`(doctorRepository.findById(id)).thenReturn(Optional.empty())
        val doctorFound = doctorServiceImpl.findDoctorById(id)
        assertThat(doctorFound).isEqualTo(null)
    }

    @Test
    fun `when save doctor then return doctor`() {
        Mockito.`when`(doctorRepository.save(doctor)).thenReturn(doctor)
        val doctorCreated = doctorServiceImpl.saveDoctor(doctor)
        assertThat(doctorCreated).isEqualTo(doctor)
    }

    @Test
    fun `when update doctor with valid id then return true`() {
        Mockito.`when`(doctorRepository.findById(id)).thenReturn(Optional.of(doctor))
        Mockito.`when`(doctorRepository.save(doctor)).thenReturn(doctor)
        assertTrue(doctorServiceImpl.updateDoctor(id, doctor))
    }

    @Test
    fun `when update doctor with not found id then return false`() {
        Mockito.`when`(doctorRepository.findById(id)).thenReturn(Optional.empty())
        assertFalse(doctorServiceImpl.updateDoctor(id, doctor))
    }

    @Test
    fun `when delete doctor with valid id then return true`() {
        Mockito.doNothing().`when`(doctorRepository).deleteById(id)
        assertTrue(doctorServiceImpl.deleteDoctorById(id))
    }

    @Test
    fun `when delete doctor with not found id then return false`() {
        Mockito.`when`(doctorRepository.deleteById(id)).thenThrow(EmptyResultDataAccessException(0))
        assertFalse(doctorServiceImpl.deleteDoctorById(id))
    }
}