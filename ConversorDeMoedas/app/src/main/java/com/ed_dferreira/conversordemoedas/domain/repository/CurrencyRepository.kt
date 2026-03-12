package com.ed_dferreira.conversordemoedas.domain.repository

import com.ed_dferreira.conversordemoedas.domain.model.CurrencyConversion

interface CurrencyRepository {
    suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Result<CurrencyConversion>
}