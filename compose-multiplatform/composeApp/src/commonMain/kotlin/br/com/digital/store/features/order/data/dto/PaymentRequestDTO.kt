package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.status.PaymentStatus
import br.com.digital.store.features.order.domain.type.PaymentType
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequestDTO(
    var status: PaymentStatus? = null,
    var type: PaymentType? = null
)
