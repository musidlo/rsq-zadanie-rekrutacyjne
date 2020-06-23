package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Visit(
        @field:NotNull @ManyToOne @JoinColumn(name = "doctor_id") val doctor: Doctor,
        @field:NotNull @ManyToOne @JoinColumn(name = "patient_id") val patient: Patient,
        @field:NotNull var date: LocalDateTime,
        @field:NotNull val place: String,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null
)