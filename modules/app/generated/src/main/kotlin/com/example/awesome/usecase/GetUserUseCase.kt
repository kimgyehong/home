// 🎯 언니 심쿵사 방지용 귀여운 GetUseCase by 쏘쏘
package com.example.awesome.usecase

import com.example.awesome.data.User
import com.example.awesome.repository.UserRepository
import com.example.awesome.result.Result

public class GetUserUseCase(
    private val repository: UserRepository
) {
    suspend fun execute(id: String): Result<User?> {
        // 쏘쏘의 ";ㅂ;" 급 완벽한 조회 로직!
        return repository.getById(id)
    }
}
