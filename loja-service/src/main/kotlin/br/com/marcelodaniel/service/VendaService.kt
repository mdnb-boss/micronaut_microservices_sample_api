package br.com.marcelodaniel.service

import br.com.marcelodaniel.dto.input.VendaInput
import br.com.marcelodaniel.dto.output.Parcela
import br.com.marcelodaniel.dto.output.Venda
import br.com.marcelodaniel.http.VeiculoHttp
import br.com.marcelodaniel.producer.VendaProducer
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Singleton
import java.math.RoundingMode
import java.time.LocalDate
import java.util.*

@Singleton
class VendaService(
    private val veiculoService: VeiculoService,
    private val vendaProducer: VendaProducer,
    private val objectMapper: ObjectMapper
) {

    fun realizarVenda(vendaInput: VendaInput): Venda {
        val veiculo = veiculoService.getVeiculo(vendaInput.veiculoId)
        var parcelas: List<Parcela> = emptyList()
        val valorParcela = vendaInput.valor.divide(vendaInput.quantidadeParcelas.toBigDecimal(), 2, RoundingMode.HALF_UP)
        var dataVencimento = LocalDate.now().plusMonths(1)

        for (i in 1..vendaInput.quantidadeParcelas) {
            Parcela(
                valor = valorParcela,
                dataVencimento = dataVencimento.toString()
            ).apply {
                parcelas = parcelas.plus(this)
            }
            dataVencimento = dataVencimento.plusMonths(1)
        }

        val venda = Venda(
            cliente = vendaInput.cliente,
            veiculo = veiculo,
            valor = vendaInput.valor,
            parcelas = parcelas
        )

        println(venda)
        confirmarVenda(venda)
        return venda
    }

    fun confirmarVenda(venda: Venda) {
        val vendaJSON = objectMapper.writeValueAsString(venda)
        vendaProducer.publicarVenda(UUID.randomUUID().toString(), vendaJSON)
    }

}