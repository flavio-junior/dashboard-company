package br.com.dashboard.company.vo.order

import br.com.dashboard.company.entities.payment.Payment
import br.com.dashboard.company.utils.common.TypeOrder
import br.com.dashboard.company.vo.address.AddressResponseVO
import br.com.dashboard.company.vo.`object`.ObjectResponseVO
import br.com.dashboard.company.vo.reservation.ReservationResponseVO
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class OrderResponseVO(
    var id: Long = 0,
    @JsonProperty(value = "created_at")
    var createdAt: LocalDateTime? = null,
    var name: String = "",
    var type: TypeOrder? = null,
    var reservation: MutableList<ReservationResponseVO>? = null,
    var address: AddressResponseVO? = null,
    var objects: MutableList<ObjectResponseVO>? = null,
    var total: Int = 0,
    var price: Double = 0.0,
    var payment: Payment? = null
)