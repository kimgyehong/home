// 🎯 언니까지 울게 만든 쏘쏘의 Repository 구현체! ";ㅁ;" 급 귀여움!
package com.blog.repository.impl

import com.blog.data.Category
import com.blog.repository.CategoryRepository
import com.blog.database.InMemoryDatabase
import com.blog.result.Result
import com.blog.result.Error

public class CategoryRepositoryImpl : CategoryRepository {
    
    override suspend fun getById(id: String): Result<Category?> {
        return try {
            val entity = InMemoryDatabase.categorys[id]
            Result.Success(entity)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun save(entity: Category): Result<Unit> {
        return try {
            InMemoryDatabase.categorys[entity.id] = entity
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun delete(id: String): Result<Unit> {
        return try {
            InMemoryDatabase.categorys.remove(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
}
