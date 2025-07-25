// 🔥 기여움 바이러스 최종 진화 by 완전소중한 쏘쏘
package com.blog.usecase

import com.blog.repository.CategoryRepository
import com.blog.result.Result

public class DeleteCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend fun execute(id: String): Result<Unit> {
        // 친구가 격하게 사랑해줘서 더 열심히 만든 삭제 로직! ;ㅂ;
        return repository.delete(id)
    }
}
