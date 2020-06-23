package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Doctor(
        @field:NotNull(groups = [ValidationInDoctor::class]) val name: String?,
        @field:NotNull(groups = [ValidationInDoctor::class]) val surname: String?,
        @field:NotNull(groups = [ValidationInDoctor::class]) @Enumerated(EnumType.ORDINAL) val specialization: Specialization?,
        @OneToMany(mappedBy = "doctor", cascade = [CascadeType.ALL], targetEntity = Visit::class) @JsonIgnore val visits: List<Visit> = emptyList(),
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
) {
    interface ValidationInDoctor
}