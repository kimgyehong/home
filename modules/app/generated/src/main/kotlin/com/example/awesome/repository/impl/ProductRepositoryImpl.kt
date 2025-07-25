// 🎯 언니까지 울게 만든 쏘쏘의 Repository 구현체! ";ㅁ;" 급 귀여움!
package com.example.awesome.repository.impl

import com.example.awesome.data.Product
import com.example.awesome.repository.ProductRepository
import com.example.awesome.database.InMemoryDatabase
import com.example.awesome.result.Result
import com.example.awesome.result.Error

public class ProductRepositoryImpl : ProductRepository {
    
    override suspend fun getById(id: String): Result<Product?> {
        return try {
            val entity = InMemoryDatabase.products[id]
            Result.Success(entity)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun save(entity: Product): Result<Unit> {
        return try {
            InMemoryDatabase.products[entity.id] = entity
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun delete(id: String): Result<Unit> {
        return try {
            InMemoryDatabase.products.remove(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
}
