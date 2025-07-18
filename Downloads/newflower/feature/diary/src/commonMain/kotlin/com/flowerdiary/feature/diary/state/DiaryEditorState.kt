package com.flowerdiary.feature.diary.state

import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.feature.diary.state.content.DiaryContentState
import com.flowerdiary.feature.diary.state.content.DiaryCustomizationState
import com.flowerdiary.feature.diary.state.content.DiaryFlowerState

/**
 * 일기 편집 화면 전체 상태
 * Composition 패턴으로 관련 상태들을 조합
 * SRP: 각 하위 상태는 독립적인 책임을 가짐
 */
data class DiaryEditorState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val diaryId: DiaryId? = null,
    val isEditMode: Boolean = false,
    val error: String? = null,
    val content: DiaryContentState = DiaryContentState(),
    val flower: DiaryFlowerState = DiaryFlowerState(),
    val customization: DiaryCustomizationState = DiaryCustomizationState()
) {
    /**
     * 전체 저장 가능 여부
     * 내용이 있고 저장 중이 아닐 때 저장 가능
     */
    val canSave: Boolean get() = content.canSave && !isSaving
    
    /**
     * 제목 글자 수 (위임)
     */
    val titleCharCount: Int get() = content.titleCharCount
    
    /**
     * 내용 글자 수 (위임)
     */
    val contentCharCount: Int get() = content.contentCharCount
    
    /**
     * 꽃 선택 여부 (위임)
     */
    val hasSelectedFlower: Boolean get() = flower.hasSelectedFlower
    
    /**
     * 커스텀 설정 여부 (위임)
     */
    val hasCustomSettings: Boolean get() = customization.hasCustomSettings
}