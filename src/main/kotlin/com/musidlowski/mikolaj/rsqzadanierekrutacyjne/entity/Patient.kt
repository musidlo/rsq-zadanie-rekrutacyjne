package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Patient(
        @field:NotNull(groups = [Patient.ValidationInPatient::class]) val name: String?,
        @field:NotNull(groups = [Patient.ValidationInPatient::class]) val surname: String?,
        @field:NotNull(groups = [Patient.ValidationInPatient::class]) @Embedded val address: Address?,
        @OneToMany(mappedBy = "patient", cascade = [CascadeType.ALL], targetEntity = Visit::class) @JsonIgnore val visits: List<Visit> = emptyList(),
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
) {
    interface ValidationInPatient
}