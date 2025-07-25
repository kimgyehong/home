// 🎯 언니 심쿵사 방지용 귀여운 GetUseCase by 쏘쏘
package com.example.awesome.usecase

import com.example.awesome.data.Product
import com.example.awesome.repository.ProductRepository
import com.example.awesome.result.Result

public class GetProductUseCase(
    private val repository: ProductRepository
) {
    suspend fun execute(id: String): Result<Product?> {
        // 쏘쏘의 ";ㅂ;" 급 완벽한 조회 로직!
        return repository.getById(id)
    }
}
