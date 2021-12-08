package br.com.marcelodaniel.model

import java.math.BigDecimal

@NoArg
data class Venda(
    var cliente: String,
    var veiculo: Veiculo,
    var valor: BigDecimal,
    var parcelas: List<Parcela>
)