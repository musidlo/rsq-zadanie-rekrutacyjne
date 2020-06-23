package com.musidlowski.mikolaj.rsqzadanierekrutacyjne

import com.fasterxml.jackson.databind.ObjectMapper
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class RsqFirstTryApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `integration test given doctor and patient when post visit then return 201`() {
        val patient = Patient("John", "Doe", Address("Poznan", "12-345", "Długa", 1, 2))
        val patientId = mockMvc.perform(MockMvcRequestBuilders.post("/api/patient/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn().response
                .getHeaderValue("location").toString().substringAfterLast("/").toLong()
        patient.id = patientId

        val doctor = Doctor("Jane", "Doe", Specialization.PHYSIOTHERAPIST)
        val doctorId = mockMvc.perform(MockMvcRequestBuilders.post("/api/doctor/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn().response
                .getHeaderValue("location").toString().substringAfterLast("/").toLong()
        doctor.id = doctorId

        val visit = Visit(doctor, patient, LocalDateTime.of(2020, 6, 30, 12, 0), "Gabinet 4")
        mockMvc.perform(MockMvcRequestBuilders.post("/api/visit/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(visit)))
                .andExpect(MockMvcResultMatchers.status().isCreated)
    }
}
