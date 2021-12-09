package br.com.marcelodaniel.api

import io.micronaut.core.async.publisher.Publishers
import io.micronaut.discovery.ServiceInstance
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.client.LoadBalancer
import io.micronaut.http.client.ProxyHttpClient
import io.micronaut.http.filter.OncePerRequestHttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.micronaut.http.uri.UriBuilder
import io.reactivex.Flowable
import jakarta.inject.Named
import org.reactivestreams.Publisher


@Filter("/**")
class GatewayFilter(
    @field:Named("serviceLoadBalancers") private val serviceLoadBalancers: Map<String, LoadBalancer>,
    private val proxyHttpClient: ProxyHttpClient
) :
    OncePerRequestHttpServerFilter() {
    override fun doFilterOnce(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {
        val serviceName = request.path.replace("^/([^/]+).*$".toRegex(), "$1")
        if (serviceLoadBalancers.containsKey(serviceName)) {
            val loadBalancer = serviceLoadBalancers[serviceName]
            return Flowable.fromPublisher(loadBalancer!!.select())
                .flatMap { serviceInstance: ServiceInstance ->
                    val finalRequest =
                        prepareRequestForTarget(request, serviceInstance)
                    proxyHttpClient.proxy(finalRequest)
                }
        }
        return Publishers.just(HttpResponse.notFound<Any>())
    }

    private fun prepareRequestForTarget(
        request: HttpRequest<*>,
        serviceInstance: ServiceInstance
    ): MutableHttpRequest<*> {
        return request.mutate()
            .uri { uri: UriBuilder ->
                uri
                    .scheme("http")
                    .host(serviceInstance.host)
                    .port(serviceInstance.port)
                    .replacePath(request.path.replace("/" + serviceInstance.id, ""))
            }
    }
}
