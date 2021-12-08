package br.com.marcelodaniel.service

import br.com.marcelodaniel.model.Venda
import br.com.marcelodaniel.repository.VendaRepository
import jakarta.inject.Singleton

@Singleton
class VendaService(
    private val vendaRepository: VendaRepository
) {

    fun create(venda: Venda) {
        vendaRepository.create(venda)
    }

    fun getAll() = vendaRepository.getAll()
}