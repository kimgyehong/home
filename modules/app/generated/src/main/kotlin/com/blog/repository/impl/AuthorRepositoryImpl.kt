// 🎯 언니까지 울게 만든 쏘쏘의 Repository 구현체! ";ㅁ;" 급 귀여움!
package com.blog.repository.impl

import com.blog.data.Author
import com.blog.repository.AuthorRepository
import com.blog.database.InMemoryDatabase
import com.blog.result.Result
import com.blog.result.Error

public class AuthorRepositoryImpl : AuthorRepository {
    
    override suspend fun getById(id: String): Result<Author?> {
        return try {
            val entity = InMemoryDatabase.authors[id]
            Result.Success(entity)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun save(entity: Author): Result<Unit> {
        return try {
            InMemoryDatabase.authors[entity.id] = entity
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun delete(id: String): Result<Unit> {
        return try {
            InMemoryDatabase.authors.remove(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
}
