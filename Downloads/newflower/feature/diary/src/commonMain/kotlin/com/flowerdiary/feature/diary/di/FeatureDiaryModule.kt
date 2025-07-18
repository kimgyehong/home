package com.flowerdiary.feature.diary.di

import com.flowerdiary.common.data.UnitOfWork
import com.flowerdiary.domain.repository.BirthFlowerRepository
import com.flowerdiary.domain.repository.DiaryRepository
import com.flowerdiary.domain.usecase.diary.*
import com.flowerdiary.domain.usecase.flower.*
import com.flowerdiary.feature.diary.facade.DiaryFacade
import com.flowerdiary.feature.diary.viewmodel.*

/**
 * Feature Diary 모듈 DI 설정
 * SRP: 의존성 그래프 정의만 담당
 * 플랫폼 중립적 의존성 주입
 */
object FeatureDiaryModule {
    
    /**
     * ViewModels 생성을 위한 팩토리
     * Koin이나 다른 DI 프레임워크에서 사용
     */
    class ViewModelFactory(
        private val getDiariesUseCase: GetDiariesUseCase,
        private val getDiaryUseCase: GetDiaryUseCase,
        private val saveDiaryUseCase: SaveDiaryUseCase,
        private val deleteDiaryUseCase: DeleteDiaryUseCase,
        private val getBirthFlowerUseCase: GetBirthFlowerUseCase,
        private val unlockBirthFlowerUseCase: UnlockBirthFlowerUseCase,
        private val recommendFlowerUseCase: RecommendFlowerUseCase,
        private val birthFlowerRepository: BirthFlowerRepository
    ) {
        
        /**
         * 일기 목록 ViewModel 생성
         */
        fun createDiaryListViewModel(): DiaryListViewModel =
            DiaryListViewModel(
                getDiariesUseCase = getDiariesUseCase,
                deleteDiaryUseCase = deleteDiaryUseCase
            )
        
        /**
         * 일기 편집 ViewModel 생성
         */
        fun createDiaryEditorViewModel(): DiaryEditorViewModel =
            DiaryEditorViewModel(
                getDiaryUseCase = getDiaryUseCase,
                saveDiaryUseCase = saveDiaryUseCase,
                getBirthFlowerUseCase = getBirthFlowerUseCase,
                recommendFlowerUseCase = recommendFlowerUseCase
            )
        
        /**
         * 도감 ViewModel 생성
         */
        fun createCollectionViewModel(): CollectionViewModel =
            CollectionViewModel(
                getBirthFlowerUseCase = getBirthFlowerUseCase,
                unlockBirthFlowerUseCase = unlockBirthFlowerUseCase,
                birthFlowerRepository = birthFlowerRepository
            )
    }
    
    /**
     * Facade 생성을 위한 팩토리
     */
    class FacadeFactory(
        private val unitOfWork: UnitOfWork,
        private val diaryRepository: DiaryRepository,
        private val birthFlowerRepository: BirthFlowerRepository,
        private val saveDiaryUseCase: SaveDiaryUseCase,
        private val unlockBirthFlowerUseCase: UnlockBirthFlowerUseCase
    ) {
        
        /**
         * DiaryFacade 생성
         */
        fun createDiaryFacade(): DiaryFacade =
            DiaryFacade(
                unitOfWork = unitOfWork,
                diaryRepository = diaryRepository,
                birthFlowerRepository = birthFlowerRepository,
                saveDiaryUseCase = saveDiaryUseCase,
                unlockBirthFlowerUseCase = unlockBirthFlowerUseCase
            )
    }
}
