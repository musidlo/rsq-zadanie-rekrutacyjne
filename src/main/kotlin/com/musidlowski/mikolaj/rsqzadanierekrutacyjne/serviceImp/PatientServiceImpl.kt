package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.serviceImp

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Patient
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository.PatientRepository
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service.PatientService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(private val repository: PatientRepository) : PatientService {

    override fun findAllPatients(): List<Patient> = repository.findAll()

    override fun findPatientById(id: Long): Patient? = repository.findByIdOrNull(id)

    override fun savePatient(patient: Patient): Patient = repository.save(patient)

    override fun updatePatient(id: Long, patient: Patient): Boolean {
        findPatientById(id) ?: return false
        patient.id = id
        savePatient(patient)
        return true
    }

    override fun deletePatientById(id: Long): Boolean {
        try {
            repository.deleteById(id)
        } catch (exception: EmptyResultDataAccessException) {
            return false
        }
        return true
    }
}