package br.com.digital.store.features.product.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.CONFIRM_UPDATE
import br.com.digital.store.components.strings.StringsUtils.NAME
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.strings.StringsUtils.QUANTITY
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.product.data.dto.UpdateProductRequestDTO
import br.com.digital.store.features.product.ui.viewmodel.ProductViewModel
import br.com.digital.store.features.product.utils.ProductUtils.NEW_NAME_PRODUCT
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdateProduct(
    id: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: ProductViewModel = getKoin().get()
    var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var price: String by remember { mutableStateOf(value = "0.0") }
    var quantity: String by remember { mutableStateOf(value = "0") }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val checkUpdateProduct = {
        if (checkUpdateProductIsNull(
                name = name,
                price = price.toDouble(),
                quantity = quantity.toInt()
            )
        ) {
            observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
        } else {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.updateProduct(
                product = UpdateProductRequestDTO(
                    id = id,
                    name = name,
                    categories = emptyList(),
                    price = price.toDouble(),
                    quantity = quantity.toInt()
                )
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        TextField(
            label = NEW_NAME_PRODUCT,
            value = name,
            isError = observer.second,
            onValueChange = {
                name = it
            },
            modifier = Modifier.weight(weight = CommonUtils.WEIGHT_SIZE_2)
        )
        TextField(
            label = PRICE,
            value = price,
            isError = observer.second,
            onValueChange = {
                price = it
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            label = QUANTITY,
            value = quantity,
            isError = observer.second,
            onValueChange = {
                quantity = it
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        TextField(
            label = NAME,
            value = name,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                name = it
            },
            modifier = Modifier.weight(weight = CommonUtils.WEIGHT_SIZE_2),
            onGo = {
                checkUpdateProduct()
            }
        )
        LoadingButton(
            onClick = {
                checkUpdateProduct()
            },
            label = CONFIRM_UPDATE,
            isEnabled = observer.first,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        DeleteProduct(
            id = id,
            goToAlternativeRoutes = goToAlternativeRoutes,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
    ObserveNetworkStateHandlerUpdateProduct(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            name = EMPTY_TEXT
            price = "0.0"
            quantity = "0"
        }
    )
}

private fun checkUpdateProductIsNull(
    name: String,
    price: Double,
    quantity: Int
): Boolean {
    return name.isEmpty() && price == 0.0 && quantity == 0
}

@Composable
private fun ObserveNetworkStateHandlerUpdateProduct(
    viewModel: ProductViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateProduct }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            onSuccessful()
        }
    )
}