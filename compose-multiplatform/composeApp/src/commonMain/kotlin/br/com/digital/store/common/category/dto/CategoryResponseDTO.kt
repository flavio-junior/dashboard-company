package br.com.digital.store.common.category.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDTO(
    val id: Long = 0,
    val name: String = ""
)