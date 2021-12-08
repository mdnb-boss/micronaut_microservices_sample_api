package br.com.marcelodaniel.service

import br.com.marcelodaniel.model.Veiculo
import br.com.marcelodaniel.repository.VeiculoRepository
import jakarta.inject.Singleton

@Singleton
class VeiculoService(
    private val veiculoRepository: VeiculoRepository
) {

    fun create(veiculo: Veiculo): Veiculo {
        return veiculoRepository.save(veiculo)
    }

    fun findById(id: Long): Veiculo {
        return veiculoRepository.findById(id).get()
    }
}