package com.ed_dferreira.conversordemoedas.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.tooling.preview.Preview
import com.ed_dferreira.conversordemoedas.ui.theme.ConversorDeMoedasTheme

@Composable
fun CurrencyField(
    currencies: List<String>,
    selectedCurrency: String,
    currencyAmount: String, //valor antes de converter
    onCurrencySelected: (String) -> Unit,
    onCurrencyAmountChanged: (String) -> Unit, // Lambda para mostrar a modificação de valores
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth(), //preencher toda a largura disponivel
            verticalAlignment = Alignment.CenterVertically, // centralizar todo o conteudo da row
        ){
            CurrencySelector(
                currencies = currencies,
                selectedCurrency = selectedCurrency, //moeda
                onCurrencySelected = onCurrencySelected, //lambda para att
            )
            OutlinedTextField(
                value = currencyAmount,
                onCurrencyAmountChanged
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun CurrencyFieldPreview() {
    ConversorDeMoedasTheme {
        CurrencyField(
            currencies = listOf("USD", "EUR", "BRL"),
            selectedCurrency = "USD",
            "100",
            {},
            onCurrencyAmountChanged = {}
        )
    }
}