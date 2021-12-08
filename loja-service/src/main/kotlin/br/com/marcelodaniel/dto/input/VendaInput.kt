package br.com.marcelodaniel.dto.input

import java.math.BigDecimal

data class VendaInput(
    val cliente: String,
    val veiculoId: Long,
    val valor: BigDecimal,
    val quantidadeParcelas: Int
)
