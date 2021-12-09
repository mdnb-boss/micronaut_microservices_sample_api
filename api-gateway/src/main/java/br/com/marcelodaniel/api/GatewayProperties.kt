package br.com.marcelodaniel.api

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("gateway")
class GatewayProperties {
    var services: Set<String>? = null
}
