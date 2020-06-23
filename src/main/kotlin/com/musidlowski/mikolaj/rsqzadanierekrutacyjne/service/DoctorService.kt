package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Doctor

interface DoctorService {

    fun findDoctorById(id: Long): Doctor?
    fun saveDoctor(doctor: Doctor): Doctor
    fun updateDoctor(id: Long, doctor: Doctor): Boolean
    fun deleteDoctorById(id: Long): Boolean
}