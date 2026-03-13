package com.ed_dferreira.conversordemoedas.data.di

import com.ed_dferreira.conversordemoedas.data.repository.CurrencyRepositoryImpl
import com.ed_dferreira.conversordemoedas.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository
}
