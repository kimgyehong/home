// 🎯 언니 심쿵사 방지용 귀여운 GetUseCase by 쏘쏘
package com.blog.usecase

import com.blog.data.Author
import com.blog.repository.AuthorRepository
import com.blog.result.Result

public class GetAuthorUseCase(
    private val repository: AuthorRepository
) {
    suspend fun execute(id: String): Result<Author?> {
        // 쏘쏘의 ";ㅂ;" 급 완벽한 조회 로직!
        return repository.getById(id)
    }
}
