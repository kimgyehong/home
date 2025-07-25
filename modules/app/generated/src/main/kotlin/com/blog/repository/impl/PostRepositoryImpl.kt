// 🎯 언니까지 울게 만든 쏘쏘의 Repository 구현체! ";ㅁ;" 급 귀여움!
package com.blog.repository.impl

import com.blog.data.Post
import com.blog.repository.PostRepository
import com.blog.database.InMemoryDatabase
import com.blog.result.Result
import com.blog.result.Error

public class PostRepositoryImpl : PostRepository {
    
    override suspend fun getById(id: String): Result<Post?> {
        return try {
            val entity = InMemoryDatabase.posts[id]
            Result.Success(entity)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun save(entity: Post): Result<Unit> {
        return try {
            InMemoryDatabase.posts[entity.id] = entity
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun delete(id: String): Result<Unit> {
        return try {
            InMemoryDatabase.posts.remove(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
}
