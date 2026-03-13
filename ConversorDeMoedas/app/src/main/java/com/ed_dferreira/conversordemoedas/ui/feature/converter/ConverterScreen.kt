package com.ed_dferreira.conversordemoedas.ui.feature.converter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Bullet
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ed_dferreira.conversordemoedas.R
import com.ed_dferreira.conversordemoedas.ui.components.CurrencyField
import com.ed_dferreira.conversordemoedas.ui.feature.converter.model.ConverterFormEvent
import com.ed_dferreira.conversordemoedas.ui.feature.converter.model.ConverterFormState
import com.ed_dferreira.conversordemoedas.ui.theme.ConversorDeMoedasTheme
import kotlinx.coroutines.selects.select
import androidx.lifecycle.viewmodel.compose.viewModel // O import essencial
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue // Necessário para o 'by' do delegate

@Composable
fun ConverterScreen() {
    val viewModel = viewModel<ConverterViewModel>()

    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val conversionState by viewModel.conversionState.collectAsStateWithLifecycle()


    ConverterContent(
        formState = formState,
        conversionState = conversionState,
        onFormEvent = viewModel::onFormEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterContent(
    formState: ConverterFormState,
    conversionState: ConverterViewModel.ConversionState,
    onFormEvent: (ConverterFormEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .imePadding(), //sobe o titulo quanto abrir o teclado
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Conversor de Moedas",
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) //pega o espaçamento do scaffold
                .padding(16.dp)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding() //respeitar as barras do dispositivo
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) { //empilha um acima do outro
                Column {
                    CurrencyField(
                        currencies = formState.fromCurrenciesList,
                        selectedCurrency = formState.fromCurrencySelected,
                        currencyAmount = formState.fromCurrencyAmount,
                        onCurrencySelected = {
                            onFormEvent(ConverterFormEvent.OnFromCurrencySelected(it))
                        },
                        onCurrencyAmountChanged = {
                            onFormEvent(ConverterFormEvent.OnFromCurrencyAmountChanged(it))
                        }
                    )

                    CurrencyField(
                        currencies = formState.toCurrenciesList,
                        selectedCurrency = formState.toCurrencySelected,
                        currencyAmount = formState.toCurrencyAmount,
                        onCurrencySelected = {
                            onFormEvent(ConverterFormEvent.OnToCurrencySelected(it))
                        },
                        onCurrencyAmountChanged = {},
                        modifier = Modifier
                            .padding(top = 8.dp),
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = Color.LightGray,
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_downward_alt),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(4.dp), //desgruda o icon
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                contentAlignment = Alignment.Center,
            ) {
                when (conversionState) {
                    ConverterViewModel.ConversionState.Idle -> {
                        // no UI
                    }

                    ConverterViewModel.ConversionState.Loading -> {
                        CircularProgressIndicator()
                    }

                    ConverterViewModel.ConversionState.Success -> {
                        Text(
                            text = "Conversão realizada com sucesso!"
                        )
                    }

                    is ConverterViewModel.ConversionState.Error -> {
                        Text(
                            text = conversionState.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f)) //empurra para a parte de baixo da tela

            Button(
                onClick = {
                    onFormEvent(ConverterFormEvent.SendConverterForm)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = 16.dp),

                ) {
                Text(
                    text = "Converter Moedas",

                    )
            }
        }
    }

}

@Preview
@Composable
private fun ConverterContentPreview() {
    ConversorDeMoedasTheme() {
        ConverterContent(
            formState = ConverterFormState(
                fromCurrenciesList = listOf("BRL", "USD", "EUR"),
                toCurrenciesList = listOf("USD", "BRL", "EUR"),
                fromCurrencySelected = "BRL",
                toCurrencySelected = "USD",
            ),
            conversionState = ConverterViewModel.ConversionState.Success,
            onFormEvent = {},
        )
    }
}