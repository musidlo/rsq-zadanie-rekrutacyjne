package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.controller

import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity.Visit
import com.musidlowski.mikolaj.rsqzadanierekrutacyjne.service.VisitService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/visit")
class VisitController(private val service: VisitService) {

    @GetMapping("/")
    fun getAllVisits(): List<Visit> = service.findAllVisits()

    @GetMapping("/{patientId}")
    fun getVisitsByPatientId(@PathVariable patientId: Long): List<Visit> = service.findVisitsByPatientId(patientId)

    @PostMapping("/")
    fun createVisit(@Validated @RequestBody visit: Visit): ResponseEntity<Any> {
        val savedVisit: Visit = service.saveVisit(visit)
        val location: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedVisit.id).toUri()
        return ResponseEntity.created(location).build()
    }

    @PatchMapping("/{id}")
    fun updateVisitDateById(@PathVariable id: Long, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) newDate: LocalDateTime): ResponseEntity<Any> {
        return when (service.updateVisitTimeById(id, newDate)) {
            true -> ResponseEntity.noContent().build()
            false -> ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteVisit(@PathVariable id: Long): ResponseEntity<Any> {
        return when (service.deleteVisitById(id)) {
            true -> ResponseEntity.noContent().build()
            false -> ResponseEntity.notFound().build()
        }
    }
}