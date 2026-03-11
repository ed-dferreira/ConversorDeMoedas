package com.ed_dferreira.conversordemoedas.ui.components

import android.icu.util.Currency
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ed_dferreira.conversordemoedas.R
import com.ed_dferreira.conversordemoedas.ui.theme.ConversorDeMoedasTheme

@Composable
fun CurrencySelector(
    currencies: List<String>,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit, //Lambda que passa o estado pra cima (elevação de estado)
    modifier: Modifier = Modifier, //Permite a modificação para reutilização
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Row( //um do lado do outro
        modifier = modifier
            .clickable {
                expanded =
                    true //chamar o codigo depois para o dropdown, quem usa o estado tem de fazer a recomposição
            }
            .padding(16.dp)
    ) {
        Text(
            text = selectedCurrency,
            fontWeight = FontWeight.Bold,
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_drop_down),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp) //espaçamento entre icone e texto
        )
    }
    Box {
        DropdownMenu(
            expanded, //chama a parte de cima do codigo e recompoe apenas onde tem a var expanded para a api ver
            {
                expanded = false
            } //quando fechado modifica o estado para esconde-lo
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = currency
                        )
                    },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false //resetar a opção
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true) //ajuda na vizualização
@Composable
private fun CurrencySelectorPreview() {
    ConversorDeMoedasTheme() { //passar o tema
        CurrencySelector(
            currencies = listOf("USD", "EUR", "BRL"),
            selectedCurrency = "USD",
            {},
        )
    }
}