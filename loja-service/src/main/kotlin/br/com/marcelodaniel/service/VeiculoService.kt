package br.com.marcelodaniel.service

import br.com.marcelodaniel.dto.output.Veiculo
import br.com.marcelodaniel.http.VeiculoHttp
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Singleton
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

@Singleton
class VeiculoService(
    private val veiculoHttp: VeiculoHttp,
    private val objectMapper: ObjectMapper
) {

    fun getVeiculo(veiculoId: Long): Veiculo {
        return veiculoHttp.findById(veiculoId).apply {
            gravarCache(this)
        }
    }

    fun gravarCache(veiculo: Veiculo) {
        val jedisPool = JedisPool(JedisPoolConfig(), "127.0.0.1", 6379)
        val jedis = jedisPool.resource
        val veiculoJSON = objectMapper.writeValueAsString(veiculo)
        jedis.set(veiculo.id.toString(), veiculoJSON)
    }

}