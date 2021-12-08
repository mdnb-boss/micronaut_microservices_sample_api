package br.com.marcelodaniel.controller

import br.com.marcelodaniel.dto.input.VendaInput
import br.com.marcelodaniel.service.VendaService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/vendas")
class VendaController(
    private val vendaService: VendaService
) {

    @Post
    fun realizarVendas(@Body vendaInput: VendaInput) {
        vendaService.realizarVenda(vendaInput)
    }

}