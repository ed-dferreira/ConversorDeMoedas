package com.ed_dferreira.conversordemoedas.data.network

import android.icu.util.Currency
import com.ed_dferreira.conversordemoedas.data.network.model.CurrencyConversionResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject

class KtorClient @Inject constructor(private val client: HttpClient)  {
    suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): CurrencyConversionResponse{
        return client.get("pair/$fromCurrency/$toCurrency/$amount").body()
    }
}