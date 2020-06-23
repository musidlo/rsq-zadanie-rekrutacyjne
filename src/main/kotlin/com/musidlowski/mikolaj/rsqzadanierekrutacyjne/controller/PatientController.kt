package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.controller

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Patient
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service.PatientService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/patient")
class PatientController(private val service: PatientService) {

    @GetMapping("/")
    fun getAllPatients(): List<Patient> = service.findAllPatients()

    @GetMapping("/{id}")
    fun getPatientById(@PathVariable id: Long): ResponseEntity<Patient> {
        val foundPatient = service.findPatientById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(foundPatient)
    }

    @PostMapping("/")
    fun createPatient(@Validated(Patient.ValidationInPatient::class) @RequestBody patient: Patient): ResponseEntity<Any> {
        val savedPatient: Patient = service.savePatient(patient)
        val location: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPatient.id).toUri()
        return ResponseEntity.created(location).build()
    }

    @PutMapping("/{id}")
    fun updatePatientById(@Validated(Patient.ValidationInPatient::class) @RequestBody patient: Patient, @PathVariable id: Long): ResponseEntity<Any> {
        return when (service.updatePatient(id, patient)) {
            true -> ResponseEntity.noContent().build()
            false -> ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletePatientById(@PathVariable id: Long): ResponseEntity<Any> {
        return when (service.deletePatientById(id)) {
            true -> ResponseEntity.noContent().build()
            false -> ResponseEntity.notFound().build()
        }
    }
}