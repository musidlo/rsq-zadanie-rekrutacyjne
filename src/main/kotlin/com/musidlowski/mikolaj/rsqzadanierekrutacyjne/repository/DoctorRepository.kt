package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.repository

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Doctor
import org.springframework.data.jpa.repository.JpaRepository

interface DoctorRepository : JpaRepository<Doctor, Long>