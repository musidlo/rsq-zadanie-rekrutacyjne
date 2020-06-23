package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Patient

interface PatientService {

    fun findAllPatients(): List<Patient>
    fun findPatientById(id: Long): Patient?
    fun savePatient(patient: Patient): Patient
    fun updatePatient(id: Long, patient: Patient): Boolean
    fun deletePatientById(id: Long): Boolean
}