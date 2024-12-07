package br.com.digital.store.features.order.data.repository

import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.toResultFlow
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.dto.OrdersResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class OrderRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : OrderRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllOpenOrders(
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<OrdersResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/dashboard/company/orders/v1/open")
                    parameters.append("page", page.toString())
                    parameters.append("size", size.toString())
                    parameters.append("sort", sort)
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun findAllClosedOrders(
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<OrdersResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path("/api/dashboard/company/orders/v1/closed")
                    parameters.append("page", page.toString())
                    parameters.append("size", size.toString())
                    parameters.append("sort", sort)
                }
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun createNewOrder(order: OrderRequestDTO): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/dashboard/company/orders/v1")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = order)
            }
        }
    }

    override fun deleteOrder(id: Long): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url(urlString = "/api/dashboard/company/orders/v1/$id")
                headers {
                    append(HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}