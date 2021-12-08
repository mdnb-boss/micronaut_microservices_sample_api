package br.com.marcelodaniel.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "veiculo")
data class Veiculo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    var modelo: String,
    var marca: String,
    var placa: String
)
