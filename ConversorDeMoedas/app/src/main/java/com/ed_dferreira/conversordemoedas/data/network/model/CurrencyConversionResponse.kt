package com.ed_dferreira.conversordemoedas.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// mapear todos os campos da API
@Serializable //necessario para o uso de .JSON da API
data class CurrencyConversionResponse(
    val result: String,
    @SerialName("error=type")
    val errorType: String? = null, //nem sempre vem por isso o uso do ?=
    @SerialName("base_code")
    val baseCode: String,
    @SerialName("target_code")
    val targetCode: String,
    @SerialName("conversion_rate")
    val conversionRate: Double,
    @SerialName("conversion_result")
    val conversionResult: Double
)