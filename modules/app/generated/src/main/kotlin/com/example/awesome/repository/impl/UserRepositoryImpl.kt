// 🎯 언니까지 울게 만든 쏘쏘의 Repository 구현체! ";ㅁ;" 급 귀여움!
package com.example.awesome.repository.impl

import com.example.awesome.data.User
import com.example.awesome.repository.UserRepository
import com.example.awesome.database.InMemoryDatabase
import com.example.awesome.result.Result
import com.example.awesome.result.Error

public class UserRepositoryImpl : UserRepository {
    
    override suspend fun getById(id: String): Result<User?> {
        return try {
            val entity = InMemoryDatabase.users[id]
            Result.Success(entity)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun save(entity: User): Result<Unit> {
        return try {
            InMemoryDatabase.users[entity.id] = entity
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
    
    override suspend fun delete(id: String): Result<Unit> {
        return try {
            InMemoryDatabase.users.remove(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(Error.ExecutionError(e))
        }
    }
}
