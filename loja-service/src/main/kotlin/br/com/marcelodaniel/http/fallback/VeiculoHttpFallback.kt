package br.com.marcelodaniel.http.fallback

import br.com.marcelodaniel.config.Conexao
import br.com.marcelodaniel.config.RedisProperties
import br.com.marcelodaniel.dto.output.Veiculo
import br.com.marcelodaniel.http.VeiculoHttp
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.retry.annotation.Fallback

@Fallback
class VeiculoHttpFallback(
    private val onjectMapper: ObjectMapper,
    private val redisProperties: RedisProperties
) : VeiculoHttp {

    override fun findById(id: Long): Veiculo {
        val jedis = Conexao.getConexao(redisProperties)
        val veiculoJSON = jedis.get(id.toString())
        return onjectMapper.readValue(veiculoJSON, Veiculo::class.java)
    }

}