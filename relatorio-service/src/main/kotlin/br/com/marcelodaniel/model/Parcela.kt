package br.com.marcelodaniel.model

import java.math.BigDecimal

@NoArg
data class Parcela(
    var valor: BigDecimal,
    var dataVencimento: String
)