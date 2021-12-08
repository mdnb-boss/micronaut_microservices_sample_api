package br.com.marcelodaniel.http.fallback

import br.com.marcelodaniel.dto.output.Veiculo
import br.com.marcelodaniel.http.VeiculoHttp
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.retry.annotation.Fallback
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

@Fallback
class VeiculoHttpFallback(
    private val onjectMapper: ObjectMapper
): VeiculoHttp {

    override fun findById(id: Long): Veiculo {
        val jedisPool = JedisPool(JedisPoolConfig(), "127.0.0.1", 6379)
        val jedis = jedisPool.resource
        val veiculoJSON = jedis.get(id.toString())
        return onjectMapper.readValue(veiculoJSON, Veiculo::class.java)
    }

}