package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.serviceImp

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Visit
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository.VisitRepository
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service.VisitService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class VisitServiceImpl(private val repository: VisitRepository) : VisitService {

    override fun findAllVisits(): List<Visit> = repository.findAll()

    override fun findVisitById(id: Long): Visit? = repository.findByIdOrNull(id)

    override fun findVisitsByPatientId(patientId: Long): List<Visit> = repository.findByPatient_Id(patientId)

    override fun saveVisit(visit: Visit): Visit = repository.save(visit)

    override fun updateVisitTimeById(id: Long, newDate: LocalDateTime): Boolean {
        val foundVisit = findVisitById(id) ?: return false
        foundVisit.date = newDate
        saveVisit(foundVisit)
        return true
    }

    override fun deleteVisitById(id: Long): Boolean {
        try {
            repository.deleteById(id)
        } catch (exception: EmptyResultDataAccessException) {
            return false
        }
        return true
    }
}