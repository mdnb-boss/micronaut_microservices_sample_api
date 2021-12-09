package br.com.marcelodaniel.config

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("redis")
class RedisProperties {
    var host: String = ""
    var port: Int = 0
}