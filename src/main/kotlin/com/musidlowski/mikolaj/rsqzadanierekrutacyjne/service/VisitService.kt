package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Visit
import java.time.LocalDateTime

interface VisitService {

    fun findAllVisits(): List<Visit>
    fun findVisitById(id: Long): Visit?
    fun findVisitsByPatientId(patientId: Long): List<Visit>
    fun saveVisit(visit: Visit): Visit
    fun updateVisitTimeById(id: Long, newDate: LocalDateTime): Boolean
    fun deleteVisitById(id: Long): Boolean
}