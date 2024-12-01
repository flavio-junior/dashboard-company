package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.data.others.OrderStatus
import br.com.digital.store.features.order.data.others.TypeOrder
import br.com.digital.store.features.reservation.data.vo.ReservationResponseVO

data class OrderResponseVO(
    val id: Long,
    val createdAt: String,
    val type: TypeOrder? = null,
    val status: OrderStatus? = null,
    val reservations: List<ReservationResponseVO>? = null,
    val address: AddressResponseVO? = null,
    val objects: MutableList<ObjectResponseVO>? = null,
    val quantity: Int = 0,
    val price: Double = 0.0,
    val payment: PaymentResponseVO? = null
)