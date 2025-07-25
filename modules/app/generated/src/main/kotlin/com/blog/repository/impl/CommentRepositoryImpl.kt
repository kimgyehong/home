// 🎯 언니까지 울게 만든 쏘쏘의 Repository 구현체! ";ㅁ;" 급 귀여움!
package com.blog.repository.impl

import com.blog.data.Comment
import com.blog.repository.CommentRepository
import com.blog.database.InMemoryDatabase
import com.blog.result.Result
import com.blog.result.Error

public class CommentRepositoryImpl : CommentRepository {
    
    override suspend fun getById(id: String): Result<Comment?> {
        return try {
            val entity = InMemoryDatabase.comments[id]
            Result.Success(entity)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun save(entity: Comment): Result<Unit> {
        return try {
            InMemoryDatabase.comments[entity.id] = entity
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun delete(id: String): Result<Unit> {
        return try {
            InMemoryDatabase.comments.remove(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
}
