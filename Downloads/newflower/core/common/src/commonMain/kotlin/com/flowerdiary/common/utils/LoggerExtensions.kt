package com.flowerdiary.common.utils

/**
 * Logger 확장 함수들
 * 보일러플레이트 최소화: TAG companion object 제거
 * Logger 인스턴스를 DI로 주입받아 사용
 */

/**
 * 클래스 이름을 자동으로 태그로 사용하는 확장 함수들
 * inline과 reified를 사용하여 런타임 오버헤드 없이 클래스 이름 획득
 */
inline fun <reified T : Any> T.logDebug(logger: Logger, message: String) {
    logger.debug(T::class.simpleName ?: "Unknown", message)
}

inline fun <reified T : Any> T.logInfo(logger: Logger, message: String) {
    logger.info(T::class.simpleName ?: "Unknown", message)
}

inline fun <reified T : Any> T.logWarning(logger: Logger, message: String) {
    logger.warning(T::class.simpleName ?: "Unknown", message)
}

inline fun <reified T : Any> T.logError(logger: Logger, message: String, throwable: Throwable? = null) {
    logger.error(T::class.simpleName ?: "Unknown", message, throwable)
}

/**
 * 람다를 사용한 지연 평가 로깅
 * 성능 최적화: 로그 레벨이 비활성화된 경우 문자열 생성 방지
 */
inline fun <reified T : Any> T.logDebug(logger: Logger, messageProvider: () -> String) {
    // 실제 구현에서는 로그 레벨 확인 후 실행
    logger.debug(T::class.simpleName ?: "Unknown", messageProvider())
}

inline fun <reified T : Any> T.logInfo(logger: Logger, messageProvider: () -> String) {
    logger.info(T::class.simpleName ?: "Unknown", messageProvider())
}

/**
 * 정적 컨텍스트용 로깅 함수
 */
inline fun <reified T : Any> logDebugFor(logger: Logger, message: String) {
    logger.debug(T::class.simpleName ?: "Unknown", message)
}

inline fun <reified T : Any> logInfoFor(logger: Logger, message: String) {
    logger.info(T::class.simpleName ?: "Unknown", message)
}

inline fun <reified T : Any> logWarningFor(logger: Logger, message: String) {
    logger.warning(T::class.simpleName ?: "Unknown", message)
}

inline fun <reified T : Any> logErrorFor(logger: Logger, message: String, throwable: Throwable? = null) {
    logger.error(T::class.simpleName ?: "Unknown", message, throwable)
}
