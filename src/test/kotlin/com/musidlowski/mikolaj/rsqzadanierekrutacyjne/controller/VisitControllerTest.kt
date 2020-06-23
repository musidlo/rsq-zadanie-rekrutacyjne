package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.*
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service.VisitService
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class VisitControllerTest {

    @MockBean
    private lateinit var visitService: VisitService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var doctor: Doctor

    private lateinit var patient: Patient

    private lateinit var visit: Visit

    private val id = 1L

    private val invalidId = "invalidId"

    @BeforeEach
    fun setUp() {
        doctor = Doctor("Jane", "Doe", Specialization.PHYSIOTHERAPIST)
        patient = Patient("John", "Doe", Address("Poznan", "12-345", "Długa", 1, 2))
        visit = Visit(doctor, patient, LocalDateTime.of(2020, 6, 30, 12, 0), "Gabinet 4")
    }

    @Test
    fun `when get all visits then return visits`() {
        val listOfVisits = listOf(visit)
        Mockito.`when`(visitService.findAllVisits()).thenReturn(listOfVisits)
        mockMvc.perform(get("/api/visit/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.[0].place").value(visit.place))
    }

    @Test
    fun `when get visits with valid patientId then return visits`() {
        val patientId = 2L
        val listOfVisits = listOf(visit)
        Mockito.`when`(visitService.findVisitsByPatientId(patientId)).thenReturn(listOfVisits)
        mockMvc.perform(get("/api/visit/$patientId")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("\$.[0].place").value(visit.place))
    }

    @Test
    fun `when get visits with not found patientId then return empty array`() {
        Mockito.`when`(visitService.findVisitsByPatientId(Mockito.anyLong())).thenReturn(emptyList())
        mockMvc.perform(get("/api/visit/$id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().string("[]"))
    }

    @Test
    fun `when get visits with invalid patientId then return 400`() {
        mockMvc.perform(get("/api/visit/$invalidId")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when post visit with valid fields then return 201`() {
        Mockito.`when`(visitService.saveVisit(visit)).thenReturn(visit)
        mockMvc.perform(post("/api/visit/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(visit)))
                .andExpect(status().isCreated)
    }

    @Test
    fun `when post visit with invalid fields then return 400`() {
        mockMvc.perform(post("/api/visit/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when patch visit to update time with valid id and time then return 204`() {
        val newDate = LocalDateTime.of(2021, 6, 30, 12, 0)
        Mockito.`when`(visitService.updateVisitTimeById(id, newDate)).thenReturn(true)
        mockMvc.perform(patch("/api/visit/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .param("newDate", newDate.toString()))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `when patch visit to update time with valid id and invalid time then return 400`() {
        mockMvc.perform(patch("/api/visit/$id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when patch visit to update time with invalid id then return 404`() {
        val newDate = LocalDateTime.of(2021, 6, 30, 12, 0)
        Mockito.`when`(visitService.updateVisitTimeById(id, newDate)).thenReturn(false)
        mockMvc.perform(patch("/api/visit/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .param("newDate", newDate.toString()))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `when delete visit with valid id then return 204`() {
        Mockito.`when`(visitService.deleteVisitById(id)).thenReturn(true)
        mockMvc.perform(delete("/api/visit/$id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `when delete visit with not found id then return 404`() {
        Mockito.`when`(visitService.deleteVisitById(id)).thenReturn(false)
        mockMvc.perform(delete("/api/visit/$id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `when delete visit with invalid id then return 400`() {
        mockMvc.perform(delete("/api/visit/$invalidId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }
}