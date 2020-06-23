package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Visit
import org.springframework.data.jpa.repository.JpaRepository

interface VisitRepository : JpaRepository<Visit, Long> {
    fun findByPatient_Id(patientId: Long): List<Visit>
}