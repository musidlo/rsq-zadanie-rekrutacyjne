package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Patient
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository : JpaRepository<Patient, Long>