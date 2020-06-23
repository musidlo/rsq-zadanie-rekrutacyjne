package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.serviceImp

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Doctor
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository.DoctorRepository
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service.DoctorService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DoctorServiceImpl (private val repository: DoctorRepository) : DoctorService  {

    override fun findDoctorById(id: Long): Doctor? = repository.findByIdOrNull(id)

    override fun saveDoctor(doctor: Doctor): Doctor = repository.save(doctor)

    override fun updateDoctor(id: Long, doctor: Doctor): Boolean {
        findDoctorById(id) ?: return false
        doctor.id = id
        saveDoctor(doctor)
        return true
    }

    override fun deleteDoctorById(id: Long): Boolean {
        try {
            repository.deleteById(id)
        } catch (exception: EmptyResultDataAccessException) {
            return false
        }
        return true
    }
}