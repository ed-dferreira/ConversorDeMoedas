package com.ed_dferreira.conversordemoedas.data.repository

import com.ed_dferreira.conversordemoedas.data.network.KtorClient
import com.ed_dferreira.conversordemoedas.domain.model.CurrencyConversion
import com.ed_dferreira.conversordemoedas.domain.repository.CurrencyRepository
import java.util.Currency

class CurrencyRepositoryImpl(
    private val ktorClient: KtorClient
) : CurrencyRepository {
    override suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Result<CurrencyConversion> {
        return runCatching {
            val response = ktorClient.convertCurrency(
                fromCurrency = fromCurrency,
                toCurrency = toCurrency,
                amount = amount,
            )
            CurrencyConversion(
                baseCode = response.baseCode,
                targetCode = response.targetCode,
                conversionRate = response.conversionRate.toString(),
                conversionResult = response.conversionResult.toString()
            )
        }
    }
}