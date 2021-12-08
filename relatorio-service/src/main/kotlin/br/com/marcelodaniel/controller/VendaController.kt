package br.com.marcelodaniel.controller

import br.com.marcelodaniel.service.VendaService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/vendas")
class VendaController(
    private val vendaService: VendaService
) {

    @Get
    fun getAll() = vendaService.getAll()

}