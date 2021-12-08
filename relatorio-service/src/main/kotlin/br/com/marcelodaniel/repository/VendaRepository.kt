package br.com.marcelodaniel.repository

import br.com.marcelodaniel.model.Venda
import jakarta.inject.Singleton
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.result.InsertOneResult

@Singleton
class VendaRepository(
    private val mongoClient: MongoClient
) {

    fun create(venda: Venda): InsertOneResult {
        return getConnection().insertOne(venda)
    }

    fun getAll(): List<Venda> {
        return getConnection().find().toList()
    }

    private fun getConnection(): MongoCollection<Venda> = mongoClient
        .getDatabase("vendas")
        .getCollection("venda", Venda::class.java)

}