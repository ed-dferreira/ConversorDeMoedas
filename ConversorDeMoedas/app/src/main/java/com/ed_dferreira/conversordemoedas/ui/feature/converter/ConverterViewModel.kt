package com.ed_dferreira.conversordemoedas.ui.feature.converter

import android.os.Message
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ed_dferreira.conversordemoedas.domain.repository.CurrencyRepository
import com.ed_dferreira.conversordemoedas.ui.feature.converter.model.ConverterFormEvent
import com.ed_dferreira.conversordemoedas.ui.feature.converter.model.ConverterFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
) : ViewModel() {
    private val _formState = MutableStateFlow(ConverterFormState())
    val formState = _formState.asStateFlow()

    private val _conversionState = MutableStateFlow<ConversionState>(ConversionState.Idle)
    val conversionState =_conversionState.asStateFlow()

    init { //valores q iram aparecer nos campos
        _formState.update {
            it.copy(
                fromCurrenciesList = listOf("BRL", "USD", "EUR"),
                toCurrenciesList = listOf("USD", "BRL", "EUR"),
                fromCurrencySelected = "BRL",
                toCurrencySelected = "USD",
            )
        }
    }

    fun onFormEvent(event: ConverterFormEvent) {
        when (event) {
            is ConverterFormEvent.OnFromCurrencySelected -> {
                _formState.update {
                    it.copy(fromCurrencySelected = event.curency)
                }
            }
            is ConverterFormEvent.OnToCurrencySelected -> {
                _formState.update {
                    it.copy(toCurrencySelected = event.curency)
                }
            }
            is ConverterFormEvent.OnFromCurrencyAmountChanged -> {
                _formState.update {
                    it.copy(fromCurrencyAmount = event.curency)
                }
            }
            ConverterFormEvent.SendConverterForm -> {
                // Se quer apenas logar, faça fora do .update ou retorne o estado atual
                Log.d("ConverterViewModel", "onFormEvent: ${_formState.value}")
                convertCurrency()
            }
        }
    }

    private fun convertCurrency(){
        viewModelScope.launch {
            val fromCurrency = _formState.value.fromCurrencySelected
            val toCurrency = _formState.value.toCurrencySelected
            val amount = _formState.value.fromCurrencyAmount.toDoubleOrNull()

            if(fromCurrency.isNotBlank() && toCurrency.isNotBlank() && amount != null) {
                _conversionState.update {
                    ConversionState.Loading
                }
                currencyRepository.convertCurrency(
                    fromCurrency = fromCurrency,
                    toCurrency = toCurrency,
                    amount = amount,
                ).fold(
                    onSuccess = { currencyConversion ->
                        _formState.update {
                            it.copy(toCurrencyAmount = currencyConversion.conversionResult)
                        }
                        _conversionState.update {
                            ConversionState.Success
                        }
                    },
                    onFailure = {error ->
                        _conversionState.update{
                            ConversionState.Error(error.message ?: "Erro Desconhecido")
                        }
                    }
                )
            } else {
                _conversionState.update{
                    ConversionState.Error("Valores Invalidos")
                }

            }
        }
    }

    sealed interface ConversionState{
        object Idle : ConversionState
        object Loading : ConversionState
        object Success: ConversionState
        data class Error(val message: String) : ConversionState
    }
}