package br.com.marcelodaniel.model

@NoArg
data class Veiculo(
    var id: Long,
    var modelo: String,
    var marca: String,
    var placa: String
)
