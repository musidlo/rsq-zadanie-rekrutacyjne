package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Address
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Patient
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service.PatientService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @MockBean
    private lateinit var patientService: PatientService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var patient: Patient

    private val id = 1L

    private val invalidId = "invalidId"

    @BeforeEach
    fun setUp() {
        patient = Patient("John", "Doe", Address("Poznan", "12-345", "Długa", 1, 2))
    }

    @Test
    fun `when get all patients then return patients`() {
        val listOfPatients = listOf(patient)
        Mockito.`when`(patientService.findAllPatients()).thenReturn(listOfPatients)
        mockMvc.perform(get("/api/patient/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.[0].name").value(patient.name!!))
    }

    @Test
    fun `when get patient with valid id then return patient`() {
        Mockito.`when`(patientService.findPatientById(id)).thenReturn(patient)
        mockMvc.perform(get("/api/patient/$id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.name").value(patient.name!!))
    }

    @Test
    fun `when get patient with not found id then return 404`() {
        mockMvc.perform(get("/api/patient/$id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `when get patient with invalid id then return 400`() {
        mockMvc.perform(get("/api/patient/$invalidId")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when post patient with valid fields then return 201`() {
        Mockito.`when`(patientService.savePatient(patient)).thenReturn(patient)
        mockMvc.perform(post("/api/patient/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated)
    }

    @Test
    fun `when post patient with invalid fields then return 400`() {
        mockMvc.perform(post("/api/patient/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when put patient with valid fields and id then return 204`() {
        Mockito.`when`(patientService.updatePatient(id, patient)).thenReturn(true)
        mockMvc.perform(put("/api/patient/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `when put patient with invalid id then return 404`() {
        Mockito.`when`(patientService.updatePatient(id, patient)).thenReturn(false)
        mockMvc.perform(put("/api/patient/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `when put patient with invalid fields and valid id then return 400`() {
        mockMvc.perform(put("/api/patient/$id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when delete patient with valid id then return 204`() {
        Mockito.`when`(patientService.deletePatientById(id)).thenReturn(true)
        mockMvc.perform(delete("/api/patient/$id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `when delete patient with not found id then return 404`() {
        mockMvc.perform(delete("/api/patient/$id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `when delete patient with invalid id then return 400`() {
        mockMvc.perform(delete("/api/patient/$invalidId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }
}