package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Doctor
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Specialization
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service.DoctorService
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
class DoctorControllerTest {

    @MockBean
    private lateinit var doctorService: DoctorService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var doctor: Doctor

    private val id = 1L

    private val invalidId = "invalidId"

    @BeforeEach
    fun setUp() {
        doctor = Doctor("John", "Doe", Specialization.PHYSIOTHERAPIST)
    }

    @Test
    fun `when get doctor with valid id then return doctor`() {
        Mockito.`when`(doctorService.findDoctorById(id)).thenReturn(doctor)
        mockMvc.perform(get("/api/doctor/$id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.name").value(doctor.name!!))
    }

    @Test
    fun `when get doctor with not found id then return 404`() {
        mockMvc.perform(get("/api/doctor/$id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `when get doctor with invalid id then return 400`() {
        mockMvc.perform(get("/api/doctor/$invalidId")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when post doctor with valid fields then return 201`() {
        Mockito.`when`(doctorService.saveDoctor(doctor)).thenReturn(doctor)
        mockMvc.perform(post("/api/doctor/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated)
    }

    @Test
    fun `when post doctor with invalid fields then return 400`() {
        mockMvc.perform(post("/api/doctor/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when put doctor with valid fields and id then return 204`() {
        Mockito.`when`(doctorService.updateDoctor(id, doctor)).thenReturn(true)
        mockMvc.perform(put("/api/doctor/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `when put doctor with invalid id then return 404`() {
        Mockito.`when`(doctorService.updateDoctor(id, doctor)).thenReturn(false)
        mockMvc.perform(put("/api/doctor/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `when put doctor with invalid fields and valid id then return 400`() {
        mockMvc.perform(put("/api/doctor/$id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when delete doctor with valid id then return 204`() {
        Mockito.`when`(doctorService.deleteDoctorById(id)).thenReturn(true)
        mockMvc.perform(delete("/api/doctor/$id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `when delete doctor with not found id then return 404`() {
        mockMvc.perform(delete("/api/doctor/$id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `when delete doctor with invalid id then return 400`() {
        mockMvc.perform(delete("/api/doctor/$invalidId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }
}