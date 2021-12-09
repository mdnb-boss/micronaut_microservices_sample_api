package br.com.marcelodaniel.config

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

object Conexao {

    fun getConexao(redisProperties: RedisProperties): Jedis {
        val jedisPool = JedisPool(JedisPoolConfig(), redisProperties.host, redisProperties.port)
        return jedisPool.resource
    }

}
