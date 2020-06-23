package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.controller

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Doctor
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service.DoctorService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/doctor")
class DoctorController(private val service: DoctorService) {

    @GetMapping("/{id}")
    fun getDoctorById(@PathVariable id: Long): ResponseEntity<Doctor> {
        val foundDoctor = service.findDoctorById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(foundDoctor)
    }

    @PostMapping("/")
    fun createDoctor(@Validated(Doctor.ValidationInDoctor::class) @RequestBody doctor: Doctor): ResponseEntity<Any> {
        val savedDoctor: Doctor = service.saveDoctor(doctor)
        val location: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDoctor.id).toUri()
        return ResponseEntity.created(location).build()
    }

    @PutMapping("/{id}")
    fun updateDoctorById(@Validated(Doctor.ValidationInDoctor::class) @RequestBody doctor: Doctor, @PathVariable id: Long): ResponseEntity<Any> {
        return when (service.updateDoctor(id, doctor)) {
            true -> ResponseEntity.noContent().build()
            false -> ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDoctorById(@PathVariable id: Long): ResponseEntity<Any> {
        return when (service.deleteDoctorById(id)) {
            true -> ResponseEntity.noContent().build()
            false -> ResponseEntity.notFound().build()
        }
    }
}