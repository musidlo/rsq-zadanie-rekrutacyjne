package com.musidlowski.mikolaj.rsqzadanierekrutacyjne.entity

import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

@Embeddable
data class Address(
        @field:NotNull val city: String,
        @field:NotNull val postalCode: String,
        @field:NotNull val street: String,
        @field:NotNull val houseNo: Int,
        val apartmentNo: Int? = null)