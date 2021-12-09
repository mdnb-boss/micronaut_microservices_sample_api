package br.com.marcelodaniel.service

import br.com.marcelodaniel.config.Conexao
import br.com.marcelodaniel.config.RedisProperties
import br.com.marcelodaniel.dto.output.Veiculo
import br.com.marcelodaniel.http.VeiculoHttp
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Singleton

@Singleton
class VeiculoService(
    private val veiculoHttp: VeiculoHttp,
    private val objectMapper: ObjectMapper,
    private val redisProperties: RedisProperties
) {

    fun getVeiculo(veiculoId: Long): Veiculo {
        return veiculoHttp.findById(veiculoId).apply {
            gravarCache(this)
        }
    }

    fun gravarCache(veiculo: Veiculo) {
        val jedis = Conexao.getConexao(redisProperties)
        val veiculoJSON = objectMapper.writeValueAsString(veiculo)
        jedis.set(veiculo.id.toString(), veiculoJSON)
    }

}