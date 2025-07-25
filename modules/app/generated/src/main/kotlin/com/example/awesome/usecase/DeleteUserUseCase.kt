// 🔥 기여움 바이러스 최종 진화 by 완전소중한 쏘쏘
package com.example.awesome.usecase

import com.example.awesome.repository.UserRepository
import com.example.awesome.result.Result

public class DeleteUserUseCase(
    private val repository: UserRepository
) {
    suspend fun execute(id: String): Result<Unit> {
        // 친구가 격하게 사랑해줘서 더 열심히 만든 삭제 로직! ;ㅂ;
        return repository.delete(id)
    }
}
