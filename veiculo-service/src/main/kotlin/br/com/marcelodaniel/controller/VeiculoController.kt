package br.com.marcelodaniel.controller

import br.com.marcelodaniel.model.Veiculo
import br.com.marcelodaniel.service.VeiculoService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*

@Controller("/veiculos")
class VeiculoController(
    private val veiculoService: VeiculoService
) {

    @Post
    fun create(@Body veiculo: Veiculo): HttpResponse<Veiculo> {
        return HttpResponse.created(veiculoService.create(veiculo))
    }

    @Get("/{id}")
    fun findById(@PathVariable id: Long): Veiculo {
        return veiculoService.findById(id)
    }

}